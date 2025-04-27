package my_project.tests.utilitsTests;

import backend.academy.utilits.LogLine;
import backend.academy.utilits.http.HttpRequest;
import backend.academy.utilits.http.HttpMethod;
import backend.academy.utilits.http.HttpVersion;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogLineTest {

    @Test
    void testLogLineInitialization() {
        String ip = "192.168.1.1";
        String name = "TestUser";
        ZonedDateTime time = ZonedDateTime.now();
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "/test/uri", HttpVersion.HTTP1_1);
        int status = 200;
        int size = 1024;
        String httpRefer = "https://referrer.com";
        String userAgent = "Mozilla/5.0";

        LogLine logLine = new LogLine(ip, name, time, httpRequest, status, size, httpRefer, userAgent);

        assertEquals(ip, logLine.ip());
        assertEquals(time, logLine.time());
        assertEquals(httpRequest, logLine.httpRequest());
        assertEquals(status, logLine.status());
        assertEquals(size, logLine.size());
    }

    @Test
    void testNullFields() {
        LogLine logLine = new LogLine(null, null, null, null, null, null, null, null);

        assertNull(logLine.ip());
        assertNull(logLine.time());
        assertNull(logLine.httpRequest());
        assertNull(logLine.status());
        assertNull(logLine.size());
    }

}
