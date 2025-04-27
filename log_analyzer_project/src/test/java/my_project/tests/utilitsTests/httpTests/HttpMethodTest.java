package my_project.tests.utilitsTests.httpTests;

import backend.academy.utilits.http.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @Test
    void testHttpMethodValues() {
        assertEquals("GET", HttpMethod.GET.method());
        assertEquals("POST", HttpMethod.POST.method());
        assertEquals("PUT", HttpMethod.PUT.method());
        assertEquals("HEAD", HttpMethod.HEAD.method());
        assertEquals("OPTIONS", HttpMethod.OPTIONS.method());
        assertEquals("PATCH", HttpMethod.PATCH.method());
        assertEquals("DELETE", HttpMethod.DELETE.method());
        assertEquals("TRACE", HttpMethod.TRACE.method());
        assertEquals("CONNECT", HttpMethod.CONNECT.method());
    }

    @Test
    void testToHttpMethod() {
        assertEquals(HttpMethod.GET, HttpMethod.toHttpMethod("GET"));
        assertEquals(HttpMethod.POST, HttpMethod.toHttpMethod("POST"));
        assertEquals(HttpMethod.PUT, HttpMethod.toHttpMethod("PUT"));
        assertEquals(HttpMethod.HEAD, HttpMethod.toHttpMethod("HEAD"));
        assertEquals(HttpMethod.OPTIONS, HttpMethod.toHttpMethod("OPTIONS"));
        assertEquals(HttpMethod.PATCH, HttpMethod.toHttpMethod("PATCH"));
        assertEquals(HttpMethod.DELETE, HttpMethod.toHttpMethod("DELETE"));
        assertEquals(HttpMethod.TRACE, HttpMethod.toHttpMethod("TRACE"));
        assertEquals(HttpMethod.CONNECT, HttpMethod.toHttpMethod("CONNECT"));
        assertEquals(HttpMethod.GET, HttpMethod.toHttpMethod("get"));
        assertEquals(HttpMethod.POST, HttpMethod.toHttpMethod("post"));
        assertNull(HttpMethod.toHttpMethod("INVALID"));
        assertNull(HttpMethod.toHttpMethod("getMethod"));
        assertNull(HttpMethod.toHttpMethod(""));

    }
}
