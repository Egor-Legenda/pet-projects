package backend.academy.utilits.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @ToString
public enum HttpVersion {
    HTTP1_0("HTTP/1.0"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2"),
    HTTP3("HTTP/3");
    @Getter
    private String version;

    public static HttpVersion toHttpVersion(String version) {
        for (HttpVersion httpVersion : HttpVersion.values()) {
            if (httpVersion.version().equals(version.toUpperCase())) {
                return httpVersion;

            }
        }
        return null;
    }

}
