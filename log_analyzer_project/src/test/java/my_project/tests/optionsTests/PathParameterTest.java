package my_project.tests.optionsTests;

import backend.academy.options.PathParameter;
import backend.academy.parsers.LogParser;
import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.LogLine;
import backend.academy.utilits.http.HttpMethod;
import backend.academy.utilits.http.HttpRequest;
import backend.academy.utilits.http.HttpVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PathParameterTest {

    private PathParameter pathParameterProcessor;
    private LogParser logParserMock;

    @BeforeEach
    void setUp() {
        logParserMock = mock(LogParser.class);
        pathParameterProcessor = new PathParameter();
    }


    @Test
    void testProcessValidHttpsUrl() {
        String testUrl = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        Map<String, Map<String, String>> inputMap = new LinkedHashMap<>();

        LogLine logLine = new LogLine(
            "127.0.0.1",
            "user",
            ZonedDateTime.now(),
            new HttpRequest(HttpMethod.toHttpMethod("GET"), "/index.html", HttpVersion.toHttpVersion("HTTP/1.1")),
            200,
            512,
            "https://referrer.com",
            "Mozilla/5.0"
        );
        when(logParserMock.parse(anyString())).thenReturn(logLine);

        Map<String, Map<String, String>> result = pathParameterProcessor.doParameter("--path", testUrl, inputMap);

        assertNotNull(result);
        assertTrue(result.containsKey("### Общая информация"));

    }

    @Test
    void testCheckLimiters() {
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();
        allInformation.put("limiters", Map.of("startTime", "2023-11-01T10:00:00Z"));

        LogLine logLine = new LogLine(
            "127.0.0.1",
            "user",
            ZonedDateTime.parse("2023-11-10T15:00:00Z"),
            new HttpRequest(HttpMethod.toHttpMethod("GET"), "/index.html", HttpVersion.toHttpVersion("HTTP/1.1")),
            200,
            512,
            "https://referrer.com",
            "Mozilla/5.0"
        );

        boolean isValid = pathParameterProcessor.satisfiesRequirements(allInformation, logLine);

        assertTrue(isValid);
    }

    @Test
    void testInvalidCheckLimiters() {
        Map<String, Map<String, String>> allInformation = new LinkedHashMap<>();
        allInformation.put("limiters", Map.of("startTime", "2023-11-20T10:00:00Z"));

        LogLine logLine = new LogLine(
            "127.0.0.1",
            "user",
            ZonedDateTime.parse("2023-11-10T15:00:00Z"),
            new HttpRequest(HttpMethod.toHttpMethod("GET"), "/index.html", HttpVersion.toHttpVersion("HTTP/1.1")),
            200,
            512,
            "https://referrer.com",
            "Mozilla/5.0"
        );

        boolean isValid = pathParameterProcessor.satisfiesRequirements(allInformation, logLine);

        assertFalse(isValid);
    }

    @Test
    void testProcessSafePath() {
        String safePath = "/valid/path/to/file.log";

        boolean isSafe = pathParameterProcessor.isSafePath(safePath);

        assertTrue(isSafe);
    }

    @Test
    void testProcessUnsafePath() {
        String unsafePath = "/invalid/../../file.log";

        boolean isSafe = pathParameterProcessor.isSafePath(unsafePath);

        assertFalse(isSafe);
    }
}
