package backend.academy.scrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ScrapperConfig(
        @NotEmpty String githubToken,
        StackOverflowCredentials stackoverflow, // Обратите внимание на lowercase
        KafkaConfig kafka) {
    public record StackOverflowCredentials(@NotEmpty String key, @NotEmpty String accessToken) {}

    public record KafkaConfig(Topics topics, ConsumerConfig consumer) {
        public record ConsumerConfig(@JsonProperty("group-id") String groupId // Соответствует group-id в yaml
                ) {}

        public record Topics(String requests, String responses, String updates, String dlq) {}
    }
}
