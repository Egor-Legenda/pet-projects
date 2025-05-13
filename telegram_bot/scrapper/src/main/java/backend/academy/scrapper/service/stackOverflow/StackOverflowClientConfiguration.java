package backend.academy.scrapper.service.stackOverflow;

import backend.academy.scrapper.ScrapperConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class StackOverflowClientConfiguration {
    private static final String BASE_URL = "https://api.stackexchange.com";

    @Bean
    public RestClient restClientStackOverflow(ScrapperConfig scrapperConfig) {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + scrapperConfig.stackoverflow().accessToken())
                .build();
    }
}
