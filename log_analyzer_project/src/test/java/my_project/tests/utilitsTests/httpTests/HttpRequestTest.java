package my_project.tests.utilitsTests.httpTests;

import backend.academy.utilits.http.HttpMethod;
import backend.academy.utilits.http.HttpRequest;
import backend.academy.utilits.http.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    @Test
    void testHttpRequestInitialization() {
        HttpRequest request = new HttpRequest(HttpMethod.GET, "/test/uri", HttpVersion.HTTP1_1);

        assertEquals(HttpMethod.GET, request.method());

        assertEquals("/test/uri", request.uri());

        assertEquals(HttpVersion.HTTP1_1, request.version());
    }

    @Test
    void testToStringMethod() {
        HttpRequest request = new HttpRequest(HttpMethod.POST, "/submit", HttpVersion.HTTP2);
        String expectedString = "HttpRequest(method=POST, uri=/submit, version=HttpVersion.HTTP2(version=HTTP/2))";

        assertEquals(expectedString, request.toString());
    }

    @Test
    void testNullValues() {

        HttpRequest request = new HttpRequest(null, null, null);

        assertNull(request.method());
        assertNull(request.uri());
        assertNull(request.version());
    }
}

