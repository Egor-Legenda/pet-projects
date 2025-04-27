package backend.academy.utilits.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @ToString
public class HttpRequest {
    @Getter
    private HttpMethod method;
    @Getter
    private String uri;
    @Getter
    private HttpVersion version;

}
