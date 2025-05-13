package backend.academy.common.dto.chat.response;

public record ChatResponse(Long chatId) {
    // Constructor
    public ChatResponse(Long chatId) {
        this.chatId = chatId;
    }
}
