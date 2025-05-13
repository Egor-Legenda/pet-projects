package backend.academy.bot;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.academy.bot.telegram.BotTelegram;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BotTelegramTest {

    @Mock
    private TelegramBot telegramBot;

    private BotTelegram botTelegram;

    @BeforeEach
    void setUp() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        BotConfig botConfig = new BotConfig("dummy-token", null, null);
        UrlConfig urlConfig = new UrlConfig("host", 8080);
        botTelegram = new BotTelegram(botConfig, urlConfig);
        botTelegram.setBot(telegramBot);
    }

    @Test
    public void testNegativeCommand() {
        // Arrange
        Update update = createUpdateWithText("Hello, world!", 12345L);
        SendResponse sendResponse = Mockito.mock(SendResponse.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        // Act
        botTelegram.handleUpdate(update);

        // Assert
        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(messageCaptor.capture());

        SendMessage capturedMessage = messageCaptor.getValue();
        assertEquals(
                "Не знаю такой команды(/help)", capturedMessage.getParameters().get("text"));
        assertEquals(12345L, capturedMessage.getParameters().get("chat_id"));
    }

    private Update createUpdateWithText(String text, long chatId) {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn(text);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(chatId);
        return update;
    }
}
