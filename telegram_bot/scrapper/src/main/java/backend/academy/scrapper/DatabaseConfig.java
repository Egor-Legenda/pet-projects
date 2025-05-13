package backend.academy.scrapper;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "spring.datasource", ignoreUnknownFields = false)
public record DatabaseConfig(
        @NotEmpty String driverClassName,
        @NotEmpty String url,
        @NotEmpty String username,
        @NotEmpty String password,
        @Value("access-type") @NotEmpty String accessType) {
    public DatabaseConfig {}
}
