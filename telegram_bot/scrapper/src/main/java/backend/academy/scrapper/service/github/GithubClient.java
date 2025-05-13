package backend.academy.scrapper.service.github;

import backend.academy.scrapper.ScrapperConfig;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
@Service
@AllArgsConstructor
@Slf4j
public class GithubClient {

    private RestClient restClient;
    private final ScrapperConfig scrapperConfig;

    @Autowired
    public GithubClient(ScrapperConfig scrapperConfig, RestClient restClient) {
        this.scrapperConfig = scrapperConfig;
        this.restClient = restClient;
    }

    private static final String baseUrl = "https://api.github.com/repos/";

    public ResourceData findData(String url) {
        ResponseEntity<GithubResponse> response = restClient
                .get()
                .uri(convertToApiUrl(url))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + scrapperConfig.githubToken())
                .retrieve()
                .toEntity(GithubResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResourceData(
                    response.getBody().getCreated_at(), response.getBody().getUpdated_at());
        }
        throw new HttpClientErrorException(response.getStatusCode());
    }

    public boolean checkLink(String url) {
        try {
            ResponseEntity<GithubResponse> response = restClient
                    .get()
                    .uri(convertToApiUrl(url))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + scrapperConfig.githubToken())
                    .retrieve()
                    .toEntity(GithubResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return true;
            }
            return false;
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Link not found", e);
            return false;
        } catch (Exception e) {
            log.error("link is not valid", e);
            return false;
        }
    }

    private String convertToApiUrl(String url) {
        return url.replace("https://github.com/", baseUrl);
    }
}
