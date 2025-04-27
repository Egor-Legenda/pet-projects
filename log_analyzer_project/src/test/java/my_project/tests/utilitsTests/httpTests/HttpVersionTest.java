package my_project.tests.utilitsTests.httpTests;

import backend.academy.utilits.http.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpVersionTest {

    @Test
    void testHttpVersionValues() {
        assertEquals("HTTP/1.0", HttpVersion.HTTP1_0.version());
        assertEquals("HTTP/1.1", HttpVersion.HTTP1_1.version());
        assertEquals("HTTP/2", HttpVersion.HTTP2.version());
        assertEquals("HTTP/3", HttpVersion.HTTP3.version());
    }

    @Test
    void testToHttpVersion() {
        assertEquals(HttpVersion.HTTP1_0, HttpVersion.toHttpVersion("HTTP/1.0"));
        assertEquals(HttpVersion.HTTP1_1, HttpVersion.toHttpVersion("HTTP/1.1"));
        assertEquals(HttpVersion.HTTP2, HttpVersion.toHttpVersion("HTTP/2"));
        assertEquals(HttpVersion.HTTP3, HttpVersion.toHttpVersion("HTTP/3"));

        assertEquals(HttpVersion.HTTP1_0, HttpVersion.toHttpVersion("http/1.0"));
        assertEquals(HttpVersion.HTTP1_1, HttpVersion.toHttpVersion("http/1.1"));

        assertNull(HttpVersion.toHttpVersion("HTTP/4.0"));
        assertNull(HttpVersion.toHttpVersion(""));
    }

    @Test
    void testToStringMethod() {
        HttpVersion version = HttpVersion.HTTP1_1;
        String expectedString = "HttpVersion.HTTP1_1(version=HTTP/1.1)";
        assertEquals(expectedString, version.toString());
    }
}
