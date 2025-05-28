package backend.academy.bot.telegram;

import backend.academy.bot.BotConfig;
import backend.academy.bot.UrlConfig;
import backend.academy.common.dto.chat.response.ChatResponse;
import backend.academy.common.dto.enums.CommandType;
import backend.academy.common.dto.kafka.request.KafkaBotRequest;
import backend.academy.common.dto.kafka.response.KafkaBotResponse;
import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.common.dto.request.RemoveLinkRequest;
import backend.academy.common.dto.response.ApiResponse;
import backend.academy.common.dto.response.LinkResponse;
import backend.academy.common.dto.response.ListLinksResponse;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SuppressFBWarnings({"ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
@Slf4j
@Component
public class BotTelegram {
    private static final int MAX_CACHE_PER_CHAT = 100;
    private static final int MAX_CACHE_CHATS = 1000;

    private static final Map<Long, Deque<String>> MESSAGE_CACHE = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Deque<String>> eldest) {
            return size() > MAX_CACHE_CHATS;
        }
    };
    private static Map<Long, DialogState> usersState;
    private static AddLinkRequest addLinkRequest;
    private final BotConfig botConfig;
    private final UrlConfig urlConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private TelegramBot bot;
    private List<Long> chatSessions;

    @Value("${app.message-transport}")
    private CommunicationMode communicationMode;

    @Autowired
    private KafkaTemplate<String, KafkaBotRequest> kafkaTemplate;

    @Value("${app.kafka.topics.requests}")
    private String requestsTopic;

    @Autowired
    public BotTelegram(BotConfig botConfig, UrlConfig urlConfig) {
        this.botConfig = botConfig;
        this.urlConfig = urlConfig;
        this.chatSessions = new LinkedList<>();
        usersState = new HashMap<>();
    }

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        bot = new TelegramBot(botConfig.telegramToken());
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                handleUpdate(update);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        scheduler.scheduleAtFixedRate(this::checkForUpdates, 0, 5, TimeUnit.SECONDS);
    }

    public void handleUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text().toLowerCase();
            long chatId = update.message().chat().id();
            if (!usersState.containsKey(chatId)) {
                usersState.put(chatId, DialogState.IDLE);
                log.atInfo()
                        .setMessage("Connecting a new user")
                        .addKeyValue("chat-id", chatId)
                        .log();
            }
            if (!chatSessions.contains(chatId)) {
                chatSessions.add(chatId);
            }
            if (messageText.equals("/start")) {
                if (communicationMode == CommunicationMode.KAFKA) {
                    sendCommand(new KafkaBotRequest(chatId, CommandType.START));
                } else {
                    callBackendRegisterClient(chatId);
                    sendMessage(chatId, "Введите /help для просмотра функционала :)");
                }

            } else if (messageText.startsWith("/help")) {
                sendMessage(
                        chatId,
                        """
                                /start - регистрация пользователя.
                                /help - вывод списка доступных команд.
                                /track <ссылка> - начать отслеживание ссылку, ввод фильтров и тегов далее.
                                /untrack <ссылка> - прекратить отслеживание ссылки.
                                /list - показать список отслеживаемых ссылок (cписок ссылок, полученных при /track)
                                /reset - сброс состояния до ожидания команды""");
            } else if (messageText.startsWith("/reset")) {
                log.atInfo()
                        .setMessage("Clearing the state")
                        .addKeyValue("chat-id", chatId)
                        .log();
                usersState.put(chatId, DialogState.IDLE);
                sendMessage(chatId, "Состояние сброшено, система ожидает команду");
            } else if (usersState.get(chatId) == DialogState.WAITING_FOR_TAGS) {
                if (!messageText.isEmpty()) {
                    addLinkRequest.setTags(List.of(messageText.split(" ")));
                }
                usersState.put(chatId, DialogState.WAITING_FOR_FILTERS);
                sendMessage(chatId, "Введите фильтры (опционально, например, user:dummy type:comment):");
            } else if (usersState.get(chatId) == DialogState.WAITING_FOR_FILTERS) {
                if (!messageText.isEmpty()) {
                    addLinkRequest.setFilters(List.of(messageText.split(" ")));
                }
                if (communicationMode == CommunicationMode.KAFKA) {
                    sendCommand(new KafkaBotRequest(
                            chatId,
                            CommandType.TRACK,
                            addLinkRequest.getLink(),
                            addLinkRequest.getFilters(),
                            addLinkRequest.getTags()));
                } else {

                    //                    LinkResponse response = callBackendAddLink(chatId, addLinkRequest);
                    callBackendAddLink(chatId, addLinkRequest);
                }
                usersState.put(chatId, DialogState.IDLE);
            } else if (messageText.startsWith("/track")) {
                String link = messageText.substring("/track".length()).trim();
                addLinkRequest = new AddLinkRequest();
                addLinkRequest.setLink(link);
                sendMessage(chatId, "Введите теги (опционально, через пробел):");
                usersState.put(chatId, DialogState.WAITING_FOR_TAGS);
            } else if (messageText.startsWith("/untrack")) {
                String link = messageText.substring("/untrack".length()).trim();
                if (link.isEmpty()) {
                    sendMessage(chatId, "Введите ссылку для удаления");
                    return;
                }
                if (communicationMode == CommunicationMode.KAFKA) {
                    sendCommand(new KafkaBotRequest(chatId, CommandType.UNTRACK, link));
                } else {
                    callBackendDeleteLink(chatId, link);
                }
            } else if (messageText.startsWith("/list")) {
                if (communicationMode == CommunicationMode.KAFKA) {
                    sendCommand(new KafkaBotRequest(chatId, CommandType.LIST));
                } else {
                    callBackendGetLinks(chatId);
                }
            } else {
                sendMessage(chatId, "Не знаю такой команды(/help)");
            }
        }
    }

    private void checkForUpdates() {
        for (Long chatId : chatSessions) {
            String url = "http://localhost:8081/api/updates/" + chatId;
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    sendMessage(chatId, response.getBody());
                }
            } catch (Exception e) {
                log.atError().setMessage("Watchdog failed").log();
            }
        }
    }

    private void callBackendRegisterClient(Long chatId) {
        String url = "http://localhost:" + urlConfig.port() + "/" + urlConfig.host() + "/tg-chat/" + chatId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<ApiResponse<ChatResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(null, headers),
                    new ParameterizedTypeReference<ApiResponse<ChatResponse>>() {});

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                ApiResponse<ChatResponse> apiResponse = responseEntity.getBody();

                log.info(
                        "Chat registered successfully. Chat ID: {}, Status: {}, Message: {}",
                        chatId,
                        apiResponse.status(),
                        apiResponse.message());

                sendMessage(chatId, apiResponse.message());
            } else {
                log.error("Failed to register chat. Chat ID: {}, Status: {}", chatId, responseEntity.getStatusCode());

                sendMessage(chatId, "Ошибка регистрации. Попробуйте позже");
            }
        } catch (HttpClientErrorException.Conflict e) {
            log.warn("Chat already registered. Chat ID: {}", chatId);
            sendMessage(chatId, "Вы уже зарегистрированы в системе!");
        } catch (RestClientException e) {
            log.error("API call failed. Chat ID: {}, Error: {}", chatId, e.getMessage());

            sendMessage(chatId, "Ошибка соединения с сервером");
        }
    }

    private void callBackendAddLink(Long chatId, AddLinkRequest request) {
        String url = "http://localhost:" + urlConfig.port() + "/" + urlConfig.host() + "/links";

        HttpHeaders headers = new HttpHeaders();
        headers.set("tg-chat-id", chatId.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<ApiResponse<LinkResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(request, headers),
                    new ParameterizedTypeReference<ApiResponse<LinkResponse>>() {});

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                ApiResponse<LinkResponse> apiResponse = responseEntity.getBody();

                log.info(
                        "Link added. Chat ID: {}, Status: {}, Message: {}, Link: {}",
                        chatId,
                        apiResponse.status(),
                        apiResponse.message(),
                        apiResponse.data() != null ? apiResponse.data().url() : "null");

                sendMessage(chatId, apiResponse.message());

            } else {
                log.error("Failed to add link. Chat ID: {}, Status: {}", chatId, responseEntity.getStatusCode());

                sendMessage(chatId, "Ошибка при добавлении ссылки");
            }
        } catch (RestClientException e) {
            log.error("API call failed. Chat ID: {}, Error: {}", chatId, e.getMessage());

            sendMessage(chatId, "Ошибка соединения с сервером");
        }
    }

    private void callBackendDeleteLink(Long chatId, String link) {
        String url = "http://localhost:" + urlConfig.port() + "/" + urlConfig.host() + "/links";
        HttpHeaders headers = new HttpHeaders();
        headers.set("tg-chat-id", chatId.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);
        RemoveLinkRequest request = new RemoveLinkRequest();
        request.setLink(link);
        try {
            ResponseEntity<ApiResponse<LinkResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    new HttpEntity<>(request, headers),
                    new ParameterizedTypeReference<ApiResponse<LinkResponse>>() {});

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                ApiResponse<LinkResponse> apiResponse = responseEntity.getBody();
                sendMessage(chatId, apiResponse.message());

            } else {
                sendMessage(chatId, "Ошибка при удалении ссылки");
            }
        } catch (RestClientException e) {
            log.error("API call failed. Chat ID: {}, Error: {}", chatId, e.getMessage());

            sendMessage(chatId, "Ошибка соединения с сервером");
        }
    }

    private void callBackendGetLinks(Long chatId) {
        String url = "http://localhost:" + urlConfig.port() + "/" + urlConfig.host() + "/links";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("tg-chat-id", chatId.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<ApiResponse<ListLinksResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<ApiResponse<ListLinksResponse>>() {});

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                ApiResponse<ListLinksResponse> apiResponse = responseEntity.getBody();
                sendMessage(chatId, apiResponse.message());

            } else {
                sendMessage(chatId, "Ошибка при получение ссылок");
            }
        } catch (RestClientException e) {
            log.error("API call failed. Chat ID: {}, Error: {}", chatId, e.getMessage());

            sendMessage(chatId, "Ошибка соединения с сервером");
        }
    }

    public void sendCommand(KafkaBotRequest request) {
        kafkaTemplate.send(requestsTopic, String.valueOf(request.getChatId()), request);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage request = new SendMessage(chatId, text);
        bot.execute(request);
    }

    @KafkaListener(
            topics = "${app.kafka.topics.responses}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "responseListenerContainerFactory")
    public void processCommand(@Payload KafkaBotResponse response) {
        try {

            sendMessage(response.getChatId(), response.getMessage());
        } catch (Exception e) {
            log.error("Command processing failed", e);
        }
    }

    private void cacheMessage(Update update) {
        Deque<String> chatMessages = MESSAGE_CACHE.computeIfAbsent(
            update.message().chat().id(),
            k -> new ArrayDeque<>(MAX_CACHE_PER_CHAT)
        );
        chatMessages.add(update.message().text());
        while (chatMessages.size() > MAX_CACHE_PER_CHAT) {
            chatMessages.removeFirst();
        }
    }

}
