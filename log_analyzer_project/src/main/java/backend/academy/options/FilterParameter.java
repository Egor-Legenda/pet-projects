package backend.academy.options;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterParameter implements Parameter {

    private static final String LIMITERS_TEXT = "limiters";

    @Override
    public Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    ) {
        Map<String, Map<String, String>> map = allInformation;
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        if ("--filter-field".equals(parameter)) {
            if (!map.containsKey(LIMITERS_TEXT)) {
                Map<String, String> l = new LinkedHashMap<>();
                l.put(arg, null);
                map.put(LIMITERS_TEXT, l);

            }

        }
        if ("--filter-value".equals(parameter)) {
            Map<String, String> limiters = map.get(LIMITERS_TEXT);
            for (Map.Entry<String, String> entry : limiters.entrySet()) {
                String fieldName = entry.getKey();
                String expectedValue = entry.getValue();
                if (expectedValue == null || "".equals(expectedValue)) {
                    limiters.put(fieldName, arg);
                }

            }
            map.put(LIMITERS_TEXT, limiters);
        }
        return map;
    }

}
