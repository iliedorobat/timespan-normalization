package ro.webdata.normalization.timespan.commons;

import java.util.List;

public class ParamsUtils {
    public static boolean contains(List<String> pairs, String comparator) {
        for (String pair : pairs) {
            String[] values = pair.split("=");

            // E.g.: --value
            if (values.length == 1) {
                String key = values[0];
                return comparator.equals(key);
            }
            // E.g.: --value="1/2 sec. 3 a. chr - sec. 2 p. chr."
            else if (values.length > 1) {
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

            if (key.equals(comparator) & values.length > 1) {
                return values[1];
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
}
