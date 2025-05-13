package backend.academy.scrapper.service;

import backend.academy.common.dto.enums.ResponseType;
import backend.academy.common.dto.kafka.request.KafkaBotRequest;
import backend.academy.common.dto.kafka.response.KafkaBotResponse;
import backend.academy.common.dto.request.AddLinkRequest;
import backend.academy.common.dto.request.RemoveLinkRequest;
import backend.academy.common.dto.response.ApiResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, KafkaBotResponse> responseTemplate;

    @Autowired
    private CommandService commandService;

    @Value("${app.kafka.topics.responses}")
    private String responseTopic;

    @KafkaListener(
            topics = "${app.kafka.topics.requests}",
            groupId = "${app.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void processCommand(@Payload KafkaBotRequest command) {
        try {
            List<String> filterNames = Optional.ofNullable(command.getFilters()).orElse(Collections.emptyList());
            List<String> tags = Optional.ofNullable(command.getTags()).orElse(Collections.emptyList());
            ApiResponse<?> response =
                    switch (command.getCommandType()) {
                        case START -> commandService.registerChat(command.getChatId());
                        case HELP -> new ApiResponse<>(
                                ResponseType.SUCCESS,
                                "Available commands: /start, /help, /track, /untrack, /list",
                                null);
                        case TRACK -> commandService.addLink(
                                command.getChatId(), new AddLinkRequest(command.getLink(), filterNames, tags));
                        case UNTRACK -> commandService.deleteLink(
                                command.getChatId(), new RemoveLinkRequest(command.getLink()));
                        case LIST -> commandService.getLinks(command.getChatId());
                        case UNKNOWN -> new ApiResponse<>(
                                ResponseType.SUCCESS, "Unknown command. Use /help to see available commands.", null);
                    };

            sendResponse(new KafkaBotResponse(
                    command.getChatId(), command.getCommandType(), response.status(), response.message()));
        } catch (Exception e) {
            log.error("Command processing failed", e);
            sendResponse(new KafkaBotResponse(
                    command.getChatId(), command.getCommandType(), ResponseType.NOT_FOUND, "Internal server error"));
        }
    }

    private void sendResponse(KafkaBotResponse response) {
        responseTemplate.send(responseTopic, response.getChatId().toString(), response);
    }
}
