package ro.webdata.normalization.timespan.ro;

import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.echo.commons.Const;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @apiNote <a href="http://math.hws.edu/eck/cs124/javanotes3/c9/ex-9-3-answer.html">Pre-processing roman numbers</a>
 */
public class TimeUtils {
    /** "Anno Domini" label */
    public static final String CHRISTUM_AD_LABEL = "AD";
    /** "Before Christ" label */
    public static final String CHRISTUM_BC_LABEL = "BC";

    /** Approximate date placeholder */
    public static final String APPROXIMATE_PLACEHOLDER = "APPROXIMATE";
    /** Unknown date placeholder */
    public static final String UNKNOWN_DATE_PLACEHOLDER = "UNKNOWN";
    /** "Anno Domini" placeholder */
    public static final String CHRISTUM_AD_PLACEHOLDER = "__AD__";
    /** "Before Christ" placeholder */
    public static final String CHRISTUM_BC_PLACEHOLDER = "__BC__";
    /** Placeholder used to separate two dates from an interval */
    public static final String INTERVAL_SEPARATOR_PLACEHOLDER = " __TO__ ";
    /** "from date" placeholder */
    public static String START_PLACEHOLDER = "START";
    /** "to date" placeholder */
    public static String END_PLACEHOLDER = "END";
    /** "year, month, day" pattern placeholder */
    public static String YMD_PLACEHOLDER = "YMD";
    /** "day, month, year" pattern placeholder */
    public static String DMY_PLACEHOLDER = "DMY";
    /** "year, month" pattern placeholder */
    public static String YM_PLACEHOLDER = "YM";
    /** "month, year" pattern placeholder */
    public static String MY_PLACEHOLDER = "MY";

    private static final TreeMap<Integer, String> arabicMap = new TreeMap<>(Collections.reverseOrder());
    private static final Map<Character, Integer> romanMap = new HashMap<>(7);

    static {
        arabicMap.put(1000, "m");
        arabicMap.put(900, "cm");
        arabicMap.put(500, "d");
        arabicMap.put(400, "cd");
        arabicMap.put(100, "c");
        arabicMap.put(90, "xc");
        arabicMap.put(50, "l");
        arabicMap.put(40, "xl");
        arabicMap.put(10, "x");
        arabicMap.put(9, "ix");
        arabicMap.put(5, "v");
        arabicMap.put(4, "iv");
        arabicMap.put(1, "i");

        romanMap.put('i', 1);
        romanMap.put('v', 5);
        romanMap.put('x', 10);
        romanMap.put('l', 50);
        romanMap.put('c', 100);
        romanMap.put('d', 500);
        romanMap.put('m', 1000);
    }


    /**
     * Map the era name to era label.<br/>
     * The era name is the original era value which has been
     * processed by using <b>getEraName</b> method
     * @param value The input value
     * @return "AD" or "BC"
     */
    public static String getEraLabel(String value) {
        return value.contains(CHRISTUM_BC_PLACEHOLDER)
                ? CHRISTUM_BC_LABEL
                : CHRISTUM_AD_LABEL;
    }

    /**
     * Extract the era from the prepared value.<br/>
     * The prepared value is the original value for which
     * era has been processed as following:
     * <ul>
     *     <li>era which are matching to TimespanRegex.AGE_BC will be mapped to CHRISTUM_BC_PLACEHOLDER</li>
     *     <li>era which are matching to TimespanRegex.AGE_AD will be mapped to CHRISTUM_AD_PLACEHOLDER</li>
     * </ul>
     * @param value The input value
     * @return "__AD__" or "__BC__"
     */
    public static String getEraName(String value) {
        return value.contains(CHRISTUM_BC_PLACEHOLDER)
                ? CHRISTUM_BC_PLACEHOLDER
                : CHRISTUM_AD_PLACEHOLDER;
    }

    /**
     * Remove the Christum notation ("__AD__" and "__BC__")
     * @param value The input value
     * @return The value without Christum notation
     */
    public static String clearChristumNotation(String value) {
        return value
                .replaceAll(CHRISTUM_BC_PLACEHOLDER, Const.EMPTY_VALUE_PLACEHOLDER)
                .replaceAll(CHRISTUM_AD_PLACEHOLDER, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
    }

    /**
     * Normalize the Christum notation (E.g.: "a.hr", "î.hr", etc.)
     * to standard formats ("__AD__" and "__BC__")
     * @param value The input value
     * @return The value contains normalized Christum notation
     */
    public static String normalizeChristumNotation(String value) {
        return value
                .replaceAll(TimespanRegex.AGE_BC, TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                .replaceAll(TimespanRegex.AGE_AD, TimeUtils.CHRISTUM_AD_PLACEHOLDER);
    }

    /**
     * Transform an integer into a roman number
     * @apiNote <a href="https://rekinyz.wordpress.com/2015/01/27/convert-roman-numerals-to-arabic-numerals-and-vice-versa-with-java/">Convert roman numbers</a>
     * @param num The target integer
     * @return The prepared roman number
     */
    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder(Const.EMPTY_VALUE_PLACEHOLDER);

        for (Integer i: arabicMap.keySet()) {
            for (int j = 1; j <= num / i; j++) {
                roman.append(arabicMap.get(i));
            }
            num %= i;
        }

        return roman.toString();
    }

    /**
     * Transform a roman number into an integer
     * @apiNote <a href="https://rekinyz.wordpress.com/2015/01/27/convert-roman-numerals-to-arabic-numerals-and-vice-versa-with-java/">Convert roman numbers</a>
     * @param string The target roman number
     * @return The preprared integer
     */
    public static Integer romanToInt(String string) {
        String romanChar = string.toLowerCase();
        int length = romanChar.length() - 1;
        int sum = 0;

        for (int i = 0; i < length; i++) {
            int crrValue = romanMap.get(romanChar.charAt(i));
            int nextValue = romanMap.get(romanChar.charAt(i + 1));

            if (crrValue < nextValue) {
                // The roman numbers are built from left to right, but short roman numbers
                // are build based on "V" and "X" characters are build from right to left.
                // So, for the case of the rest of roman characters, the roman number is
                // INCORRECT if it's build from right to left.
                // E.g.: "XIC" (89) is incorrect. The correct number is "LXXXIX" (89).
                if (nextValue > 10) {
                    return null;
                }
                sum -= crrValue;
            } else {
                sum += crrValue;
            }
        }

        sum += romanMap.get(romanChar.charAt(length));

        return sum;
    }

    /**
     * Convert an integer to its ordinal
     * @apiNote <a href="https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal">Convert an integer to its ordinal</a>
     * @param value The target integer
     * @return The ordinal number
     */
    public static String getOrdinal(int value) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (value % 100) {
            case 11:
            case 12:
            case 13:
                return value + "th";
            default:
                return value + suffixes[value % 10];
        }
    }
}
