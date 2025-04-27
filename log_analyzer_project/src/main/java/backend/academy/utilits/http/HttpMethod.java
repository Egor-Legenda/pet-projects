package backend.academy.utilits.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT");

    @Getter
    private String method;

    public static HttpMethod toHttpMethod(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.method().equals(method.toUpperCase())) {
                return httpMethod;

            }
        }
        return null;
    }
}
