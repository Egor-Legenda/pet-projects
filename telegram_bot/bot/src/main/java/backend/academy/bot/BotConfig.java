package backend.academy.bot;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record BotConfig(@NotEmpty String telegramToken, String messageTransport, KafkaProperties kafka) {
    public record KafkaProperties(TopicsProperties topics) {
        public record TopicsProperties(String requests, String responses, String updates, String dlq) {}
    }
}
