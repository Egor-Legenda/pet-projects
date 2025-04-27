package backend.academy.options;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public interface Parameter {
    Map<String, Map<String, String>> doParameter(
        String parameter,
        String arg,
        Map<String, Map<String, String>> allInformation
    );

    default Integer maxKeyLength(Map<String, ?> map) {
        int maxKeyLength = 0;
        for (String key : map.keySet()) {
            maxKeyLength = Math.max(maxKeyLength, key.length());
        }
        return maxKeyLength;
    }

    default Integer maxValueLength(Map<?, String> map) {
        int maxValueLength = 0;
        for (String value : map.values()) {
            maxValueLength = Math.max(maxValueLength, value.length());
        }
        return maxValueLength;
    }

    default Map<String, String> countResource(Map<String, String> currentCount, String newResource) {
        currentCount.put(newResource,
            String.valueOf(Integer.parseInt(currentCount.getOrDefault(newResource, "0")) + 1));
        return currentCount;
    }

    default Map<String, String> countIpAddress(Map<String, String> currentCount, String newIp) {
        currentCount.put(newIp, String.valueOf(Integer.parseInt(currentCount.getOrDefault(newIp, "0")) + 1));
        return currentCount;
    }

    default Map<String, String> countMethod(Map<String, String> currentCount, String newMethod) {
        currentCount.put(newMethod, String.valueOf(Integer.parseInt(currentCount.getOrDefault(newMethod, "0")) + 1));
        return currentCount;
    }

    default Map<String, String> countStatusResponse(Map<String, String> currentCount, String newStatus) {
        currentCount.put(newStatus, String.valueOf(Integer.parseInt(currentCount.getOrDefault(newStatus, "0")) + 1));
        return currentCount;
    }

    default ZonedDateTime timeBegin(ZonedDateTime currentMin, ZonedDateTime newTime) {
        return (currentMin == null || newTime.isBefore(currentMin)) ? newTime : currentMin;
    }

    default ZonedDateTime timeEnd(ZonedDateTime currentMax, ZonedDateTime newTime) {
        return (currentMax == null || newTime.isAfter(currentMax)) ? newTime : currentMax;
    }

    default int countRequest(int currentCount) {
        return currentCount + 1;
    }

    default double sizeResponse(List<Integer> currentSizes) {
        double percentile = 0.95;
        List<Integer> sortedSizes = currentSizes.stream().sorted().toList();
        try {
            return sortedSizes.get((int) Math.ceil(percentile * sortedSizes.size()) - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0.0;
        }
    }

    default double calculateAverageSize(double currentAverage, int currentCount, int newSize) {
        return currentAverage + ((newSize - currentAverage) / (currentCount + 1));
    }

    default ZonedDateTime parseDate(String dateString) {
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ZoneId zoneId = ZoneId.systemDefault();

        LocalDate date = null;

        try {
            if (dateString.matches("\\d{4}")) {
                date = LocalDate.parse(dateString, yearFormatter).withMonth(1).withDayOfMonth(1);
            } else if (dateString.matches("\\d{4}-\\d{2}")) {
                date = LocalDate.parse(dateString, yearMonthFormatter).withDayOfMonth(1);
            } else if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                date = LocalDate.parse(dateString, fullDateFormatter);
            }

        } catch (Exception e) {
            return null;
        }
        return date != null ? date.atStartOfDay(zoneId) : null;

    }
}
