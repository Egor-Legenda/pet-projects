package backend.academy.parsers;

import backend.academy.utilits.LogLine;
import backend.academy.utilits.http.HttpMethod;
import backend.academy.utilits.http.HttpRequest;
import backend.academy.utilits.http.HttpVersion;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private static final Pattern LOG_PATTERN = Pattern.compile(
        "^(\\S+) (\\S+) - \\[(.*?)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+) \"(.*?)\" \"(.*?)\"$"
    );

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public LogLine parse(String log) {
        if (log == null) {
            return null;
        }
        Matcher matcher = LOG_PATTERN.matcher(log);
        if (!matcher.matches()) {
            return null;
        }
        try {

            Integer count = 1;
            String ip = matcher.group(count);
            count += 1;
            String name = matcher.group(count);
            count += 1;
            ZonedDateTime time = ZonedDateTime.parse(matcher.group(count), DATE_FORMATTER);
            count += 1;
            HttpMethod method = HttpMethod.toHttpMethod(matcher.group(count));
            count += 1;
            String uri = matcher.group(count);
            count += 1;
            HttpVersion version = HttpVersion.toHttpVersion(matcher.group(count));
            HttpRequest httpRequest = new HttpRequest(method, uri, version);
            count += 1;
            int status = Integer.parseInt(matcher.group(count));
            count += 1;
            int size = Integer.parseInt(matcher.group(count));
            count += 1;
            String httpRefer = matcher.group(count);
            count += 1;
            String userAgent = matcher.group(count);

            return new LogLine(ip, name, time, httpRequest, status, size, httpRefer, userAgent);
        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }
    }

}
