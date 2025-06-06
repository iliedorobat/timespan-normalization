package ro.webdata.normalization.timespan.commons;

import java.util.List;

public class ParamsUtils {
    public static boolean contains(List<String> pairs, String comparator) {
        for (String pair : pairs) {
            String[] values = pair.split("=");

            // E.g.:
            //  * values.length == 1 => --historicalOnly
            //  * values.length > 1 => --historicalOnly=true
            if (values.length > 0) {
                String key = values[0];

                if (comparator.equals(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getValue(List<String> pairs, String comparator) {
        for (String pair : pairs) {
            String[] values = pair.split("=");
            String key = values[0];

            if (key.equals(comparator)) {
                if (values.length > 1) {
                    // E.g.: --historicalOnly=true
                    return values[1];
                } else {
                    // E.g.: --historicalOnly
                    return "true";
                }
            }
        }

        return null;
    }

    public static boolean historicalOnly(List<String> pairs) {
        String value = getValue(pairs, "--historicalOnly");

        return Boolean.parseBoolean(value);
    }

    public static boolean sanitize(List<String> pairs) {
        String value = getValue(pairs, "--sanitize");

        return Boolean.parseBoolean(value);
    }

    public static boolean py4j(List<String> pairs) {
        String value = getValue(pairs, "--py4j");

        return Boolean.parseBoolean(value);
    }
}
