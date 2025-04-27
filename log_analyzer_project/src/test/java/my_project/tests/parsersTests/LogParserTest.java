package my_project.tests.parsersTests;

import backend.academy.parsers.LogParser;
import backend.academy.utilits.LogLine;
import backend.academy.utilits.http.HttpMethod;
import backend.academy.utilits.http.HttpRequest;
import backend.academy.utilits.http.HttpVersion;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {

    @Test
    void testParseValidLog() {
        LogParser logParser = new LogParser();
        String log = "192.168.1.1 - - [18/Nov/2024:13:37:43 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"Mozilla/5.0\"";

        LogLine result = logParser.parse(log);

        assertNotNull(result);
        assertEquals("192.168.1.1", result.ip());
        assertEquals("-", result.name());
        assertEquals(ZonedDateTime.parse("2024-11-18T13:37:43+00:00"), result.time());
        assertEquals(200, result.status());
        assertEquals(1234, result.size());
        assertEquals("-", result.httpRefer());
        assertEquals("Mozilla/5.0", result.userAgent());
    }

    @Test
    void testParseLogWithMissingFields() {
        LogParser logParser = new LogParser();
        String log = "144.76.151.58 - - [17/May/2015:08:05:54 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"";

        LogLine result = logParser.parse(log);
        assertEquals("144.76.151.58", result.ip());
        assertEquals("-", result.name());
        assertEquals(ZonedDateTime.parse("2015-05-17T08:05:54Z"), result.time());
        assertEquals(304, result.status());
        assertEquals(0, result.size());

    }

    @Test
    void testParseInvalidLog() {
        LogParser logParser = new LogParser();
        String log = "Invalid log format";

        LogLine result = logParser.parse(log);

        assertNull(result);
    }

    @Test
    void testParseLogWithNull() {
        LogParser logParser = new LogParser();

        LogLine result = logParser.parse(null);
        assertNull(result);
    }

    @Test
    void testParseLogWithInvalidDateFormat() {
        LogParser logParser = new LogParser();
        String log = "192.168.1.1 - - [18-Nov-2024:13:37:43 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"Mozilla/5.0\"";

        LogLine result = logParser.parse(log);
        assertNull(result);
    }
}

