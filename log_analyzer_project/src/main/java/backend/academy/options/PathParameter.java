package backend.academy.options;

import backend.academy.parsers.LogParser;
import backend.academy.print.ConsolePrinter;
import backend.academy.utilits.LogLine;
import backend.academy.utilits.http.HttpRequest;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.HttpsURLConnection;

@SuppressFBWarnings({"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", "DCN_NULLPOINTER_EXCEPTION", "PATH_TRAVERSAL_IN"})
public class PathParameter implements Parameter {
    private final LogParser logParser = new LogParser();
    private final Integer timeConnect = 5000;
    private final Integer codeResponseOk = 200;
    private final static String GENERAL_INFORMATION_TEXT = "### Общая информация";
    private final static String METHOD_REQUEST_COUNT_TEXT = "### Методы запросов и их количество";
    private final static String TIMES_START_TEXT = "Время начала";
    private final static String TIMES_END_TEXT = "Время Конца";
    private final static String REQUESTED_RESOURCES_TEXT = "### Запрашиваемые ресурсы";
    private final static String CODES_RESPONSE_TEXT = "### Коды ответов";
    private final static String COUNTS_IP_TEXT = "### Количество ip адресов";
    private final static String COUNTS_TEXT = "Количество";
    private final static String AVERAGE_SIZE_RESPONSE_TEXT = "Средний размер ответа";
    private final static String COUNT_REQUEST_TEXT = "Количество запросов";
    private final ConsolePrinter consolePrinter = new ConsolePrinter();
    List<Integer> sizes = new ArrayList<>();

    @Override @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    public Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    ) {
        Map<String, Map<String, String>> map = (allInformation != null) ? allInformation : new LinkedHashMap<>();

        if (arg.startsWith("https://")) {
            map = processURL(map, arg);
        } else {
            map = processPath(map, arg);
        }

        Double size95 = sizeResponse(sizes);
        try {
            map.get(GENERAL_INFORMATION_TEXT).put("95 процентиль", String.valueOf(size95));
        } catch (NullPointerException e) {

        }
        return map;
    }

    public boolean isSafePath(String path) {
        return path != null && !path.contains("..") && !path.contains("~");
    }

    private boolean matchFilePattern(java.nio.file.Path filePath, String filePattern) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + filePattern);
        return matcher.matches(filePath.getFileName());
    }

    @SuppressFBWarnings("URLCONNECTION_SSRF_FD")
    private Map<String, Map<String, String>> processURL(
        Map<String, Map<String, String>> map,
        String arg
    ) {
        AtomicReference<Map<String, Map<String, String>>> sourceLogStats = new AtomicReference<>(map);
        try {
            URL url = new URL(arg.trim());

            if (!"https".equalsIgnoreCase(url.getProtocol())) {
                consolePrinter.println("Недопустимый протокол");
                return map;
            }

            InetAddress address = InetAddress.getByName(url.getHost());
            if (address.isLoopbackAddress() || address.isSiteLocalAddress() || address.isLinkLocalAddress()) {
                consolePrinter.println("Адрес не найден");
                return map;
            }

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(timeConnect);
            connection.setReadTimeout(timeConnect);

            int responseCode = connection.getResponseCode();
            if (responseCode == codeResponseOk) {
                try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        LogLine l = logParser.parse(line);
                        if (satisfiesRequirements(map, l)) {
                            sourceLogStats.set(averageWithNewData(sourceLogStats.get(), l, arg));
                        }
                    }
                }
            } else {
                consolePrinter.println("Ошибка: HTTP код " + responseCode);
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            consolePrinter.println("Неверный URL: " + arg);
        } catch (IOException e) {
            consolePrinter.println("Ошибка ввода-вывода при чтении HTTPS");
        } catch (Exception e) {
            consolePrinter.println("Неизвестная ошибка при работе с HTTPS: " + e.getMessage());
        }
        return map;
    }

    private Map<String, Map<String, String>> processPath(
        Map<String, Map<String, String>> map,
        String arg
    ) {
        AtomicReference<Map<String, Map<String, String>>> sourceLogStats = new AtomicReference<>(map);


        try {
            if (!isSafePath(arg)) {
                consolePrinter.println("Недопустимый путь: " + arg);
                return null;
            }

            java.nio.file.Path startPath = Paths.get(arg);
            if (startPath == null || startPath.getFileName() == null) {
                consolePrinter.println("Некорректный файл: " + arg);
                return null;
            }

            String filePattern = startPath.getFileName().toString();
            try {
                startPath = startPath.toAbsolutePath();

            } catch (InvalidPathException e) {
                consolePrinter.println("Некорректный путь: " + arg);
                return null;
            }

            java.nio.file.Path baseDirectory =
                startPath.getParent() != null ? startPath.getParent() : Paths.get(".");
            Files.walk(baseDirectory)
                .filter(path -> matchFilePattern(path, filePattern))
                .forEach(filePath -> {
                    try (BufferedReader reader = new BufferedReader(
                        new FileReader(filePath.toFile(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            LogLine l = logParser.parse(line);
                            if (l != null) {
                                sourceLogStats.set(averageWithNewData(sourceLogStats.get(), l, arg));
                            }
                        }
                    } catch (IOException e) {
                        consolePrinter.println("Ошибка чтения");
                    }
                });
        } catch (IOException e) {
            consolePrinter.println("Ошибка чтения файла");
        }
        return map;
    }

    public Map<String, Map<String, String>> averageWithNewData(
        Map<String, Map<String, String>> map,
        LogLine line,
        String path
    ) {
        Map<String, String> mapInformation =
            map.getOrDefault(GENERAL_INFORMATION_TEXT, new LinkedHashMap<String, String>());
        if (!map.containsKey(GENERAL_INFORMATION_TEXT)) {
            mapInformation.put("Метрика", "Значение");
            mapInformation.put("Путь до файла", path);
        }

        if (!mapInformation.containsKey(TIMES_START_TEXT)) {
            ZonedDateTime beginTime = timeBegin(line.time(), line.time());
            mapInformation.put(TIMES_START_TEXT, beginTime.toString());
        } else {
            ZonedDateTime beginTime = timeBegin(ZonedDateTime.parse(mapInformation.get(TIMES_START_TEXT)), line.time());
            mapInformation.put(TIMES_START_TEXT, beginTime.toString());
        }
        if (!map.containsKey(TIMES_END_TEXT)) {
            ZonedDateTime endTime = timeEnd(line.time(), line.time());
            mapInformation.put(TIMES_END_TEXT, endTime.toString());
        } else {
            ZonedDateTime endTime = timeEnd(ZonedDateTime.parse(mapInformation.get(TIMES_END_TEXT)), line.time());
            mapInformation.put(TIMES_END_TEXT, endTime.toString());
        }

        Integer countRequest;
        if (!mapInformation.containsKey(COUNT_REQUEST_TEXT)) {
            countRequest = countRequest(0);
            mapInformation.put(COUNT_REQUEST_TEXT, countRequest.toString());

        } else {
            countRequest = countRequest(Integer.parseInt(mapInformation.get(COUNT_REQUEST_TEXT)));
            mapInformation.put(COUNT_REQUEST_TEXT, countRequest.toString());
        }
        if (!mapInformation.containsKey(AVERAGE_SIZE_RESPONSE_TEXT)) {

            Double averageSize = calculateAverageSize(0, 0, line.size());
            mapInformation.put(AVERAGE_SIZE_RESPONSE_TEXT, averageSize.toString());
        } else {
            Double averageSize = calculateAverageSize(
                Double.parseDouble(mapInformation.get(AVERAGE_SIZE_RESPONSE_TEXT)),
                Integer.parseInt(mapInformation.get(COUNT_REQUEST_TEXT)) - 1, line.size());
            mapInformation.put(AVERAGE_SIZE_RESPONSE_TEXT, averageSize.toString());

        }
        sizes.add(line.size());

        map.put(GENERAL_INFORMATION_TEXT, mapInformation);
        Map<String, String> mapMethods = map.getOrDefault(METHOD_REQUEST_COUNT_TEXT, new LinkedHashMap<>());
        if (!map.containsKey(METHOD_REQUEST_COUNT_TEXT)) {
            mapMethods.put("Метод", COUNTS_TEXT);
        }
        mapMethods = countMethod(mapMethods, line.httpRequest().method().toString().toLowerCase());
        map.put(METHOD_REQUEST_COUNT_TEXT, mapMethods);

        Map<String, String> mapResources = map.getOrDefault(REQUESTED_RESOURCES_TEXT, new LinkedHashMap<>());
        if (!map.containsKey(REQUESTED_RESOURCES_TEXT)) {
            mapResources.put("Ресурс", COUNTS_TEXT);
        }
        mapResources = countResource(mapResources, line.httpRequest().uri());
        map.put(REQUESTED_RESOURCES_TEXT, mapResources);

        Map<String, String> mapCodes = map.getOrDefault(CODES_RESPONSE_TEXT, new LinkedHashMap<>());
        if (!map.containsKey(CODES_RESPONSE_TEXT)) {
            mapCodes.put("Код ответа", COUNTS_TEXT);
        }
        mapCodes = countStatusResponse(mapCodes, line.status().toString());
        map.put(CODES_RESPONSE_TEXT, mapCodes);

        Map<String, String> mapIp = map.getOrDefault(COUNTS_IP_TEXT, new LinkedHashMap<>());
        if (!map.containsKey(COUNTS_IP_TEXT)) {
            mapIp.put("Ip адрес", COUNTS_TEXT);
        }
        mapIp = countIpAddress(mapIp, line.ip());
        map.put(COUNTS_IP_TEXT, mapIp);
        return map;

    }

    public boolean satisfiesRequirements(Map<String, Map<String, String>> allInformation, LogLine logLine) {
        Map<String, String> limiters = allInformation.get("limiters");

        if (limiters == null) {
            return true;
        }

        boolean isValid = true;

        for (Map.Entry<String, String> entry : limiters.entrySet()) {
            String fieldName = entry.getKey();
            String expectedValue = entry.getValue();

            if ("startTime".equals(fieldName)) {
                isValid = logLine.time().isAfter(ZonedDateTime.parse(expectedValue));
            } else if ("endTime".equals(fieldName)) {
                isValid = logLine.time().isBefore(ZonedDateTime.parse(expectedValue));
            } else {
                isValid = validateField(logLine, fieldName, expectedValue);
            }
            if (!isValid) {
                break;
            }
        }

        return isValid;
    }

    @SuppressFBWarnings("RFI_SET_ACCESSIBLE")
    private boolean validateField(LogLine logLine, String fieldName, String expectedValue) {
        try {
            Field field = LogLine.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object actualValue = field.get(logLine);
            return actualValue != null && actualValue.toString().equals(expectedValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field field = HttpRequest.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object actualValue = field.get(logLine.httpRequest());
                return actualValue != null && actualValue.toString().equals(expectedValue);
            } catch (NoSuchFieldException | IllegalAccessException innerException) {
                return false;
            }
        }
    }

}
