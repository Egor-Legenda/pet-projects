package backend.academy.options;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class FromParameter implements Parameter {

    @Override
    public Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    ) {
        Map<String, Map<String, String>> map = allInformation;
        if (allInformation == null) {
            map = new LinkedHashMap<>();
        }
        ZonedDateTime time = parseDate(arg);

        if (time != null) {
            map.put("limiters", Map.of("startTime", time.toString()));
        }

        return map;
    }
}
