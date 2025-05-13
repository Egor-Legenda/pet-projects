package backend.academy.scrapper.service.stackOverflow;

import backend.academy.scrapper.ScrapperConfig;
import backend.academy.scrapper.service.stackOverflow.dto.QuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class StackOverflowClientImpl implements StackOverflowClient {
    private final RestClient restClient;
    private final ScrapperConfig scrapperConfig;

    @Autowired
    public StackOverflowClientImpl(ScrapperConfig scrapperConfig, RestClient restClient) {
        this.scrapperConfig = scrapperConfig;
        this.restClient = restClient;
    }

    @Override
    public QuestionResponse findQuestion(String url) {
        String questionId = extractQuestionId(url);
        try {
            ResponseEntity<QuestionResponse> response = restClient
                    .get()
                    .uri("/2.3/questions/{id}?site=stackoverflow", questionId)
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + scrapperConfig.stackoverflow().accessToken())
                    .retrieve()
                    .toEntity(QuestionResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
            throw new HttpClientErrorException(response.getStatusCode());
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Вопрос не найден: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к StackOverflow: " + e.getMessage());
        }
    }

    private String extractQuestionId(String url) {
        String[] parts = url.split("/");
        if (parts.length >= 4 && parts[2].equals("stackoverflow.com") && parts[3].equals("questions")) {
            return parts[4];
        }
        throw new IllegalArgumentException("Некорректная ссылка на вопрос StackOverflow: " + url);
    }

    public boolean checkStackOverflow(String url) {
        String[] parts = url.split("/");
        if (parts.length >= 4 && parts[2].equals("stackoverflow.com") && parts[3].equals("questions")) {
            return true;
        }
        return false;
    }
}
