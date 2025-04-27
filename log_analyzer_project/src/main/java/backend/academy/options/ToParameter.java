package backend.academy.options;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ToParameter implements Parameter {

    @Override
    public Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    ) {
        Map<String, Map<String, String>> map = allInformation;
        ZonedDateTime time = parseDate(arg);
        if (allInformation == null) {
            map = new LinkedHashMap<>();
        }
        if (time != null) {
            map.put("limiters", Map.of("endTime", time.toString()));
        }

        return map;
    }
}
