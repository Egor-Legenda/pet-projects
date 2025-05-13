package backend.academy.common.dto.kafka;

import java.time.Instant;

public abstract class KafkaMessage {
    private Long chatId;
    private Instant timestamp = Instant.now();

    public KafkaMessage(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
