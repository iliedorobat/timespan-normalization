package ro.webdata.normalization.timespan.commons;

import java.util.List;

import static py4j.GatewayServer.DEFAULT_PORT;

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

    public static boolean python(List<String> pairs) {
        String value = getValue(pairs, "--python");

        return Boolean.parseBoolean(value);
    }

    public static int gatewayPort(List<String> pairs) {
        String value = getValue(pairs, "--gateway-port");

        if (value == null)
            return DEFAULT_PORT;

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return DEFAULT_PORT;
        }
    }
}
