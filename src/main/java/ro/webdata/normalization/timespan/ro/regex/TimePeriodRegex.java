package ro.webdata.normalization.timespan.ro.regex;

/**
 * Regular expressions for those time intervals that are stored
 * as ages (centuries and millenniums)
 */
public class TimePeriodRegex {
    private TimePeriodRegex() {}

    private static final String REGEX_OR = TimespanRegex.REGEX_OR;
    private static final String REGEX_PUNCTUATION_UNLIMITED = TimespanRegex.REGEX_PUNCTUATION_UNLIMITED;
    private static final String REGEX_INTERVAL_DELIMITER = TimespanRegex.REGEX_INTERVAL_DELIMITER;

    private static final String CASE_INSENSITIVE = TimespanRegex.CASE_INSENSITIVE;
    private static final String TEXT_START = TimespanRegex.TEXT_START;
    private static final String TEXT_END = TimespanRegex.TEXT_END;

    private static final String AGES_ARABIC_NOTATION = TimespanRegex.AGES_ARABIC_NOTATION;
    private static final String AGES_ROMAN_NOTATION = TimespanRegex.AGES_ROMAN_NOTATION;
    private static final String CENTURY_NOTATION = TimespanRegex.CENTURY_NOTATION;
    private static final String MILLENNIUM_NOTATION = TimespanRegex.MILLENNIUM_NOTATION;

    private static final String FIRST_HALF = TimespanRegex.FIRST_HALF;
    private static final String SECOND_HALF = TimespanRegex.SECOND_HALF;
    private static final String MIDDLE_OF = TimespanRegex.MIDDLE_OF;

    private static final String FIRST_QUARTER = TimespanRegex.FIRST_QUARTER;
    private static final String SECOND_QUARTER = TimespanRegex.SECOND_QUARTER;
    private static final String THIRD_QUARTER = TimespanRegex.THIRD_QUARTER;
    private static final String FORTH_QUARTER = TimespanRegex.FORTH_QUARTER;

    // CENTURY TIME PERIOD (ROMAN NOTATION)
    private static final String CENTURY_ROMAN = CENTURY_NOTATION + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String CENTURY_ROMAN_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ROMAN;
    private static final String CENTURY_ROMAN_OPTIONS =
            "("
                + "(" + CENTURY_ROMAN_FIRST_HALF + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_SECOND_HALF + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_MIDDLE_OF + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_FIRST_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_SECOND_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_THIRD_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_FORTH_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN + ")"
            + ")";
    private static final String CENTURY_ROMAN_INTERVAL = CENTURY_ROMAN_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + CENTURY_ROMAN_OPTIONS + REGEX_OR
                + AGES_ROMAN_NOTATION
            + ")";

    // CENTURY TIME PERIOD (ARABIC NOTATION)
    private static final String CENTURY_ARABIC = CENTURY_NOTATION + REGEX_PUNCTUATION_UNLIMITED + AGES_ARABIC_NOTATION;
    private static final String CENTURY_ARABIC_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + CENTURY_ARABIC;
    private static final String CENTURY_ARABIC_OPTIONS =
            "("
                + "(" + CENTURY_ARABIC_FIRST_HALF + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_SECOND_HALF + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_MIDDLE_OF + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_FIRST_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_SECOND_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_THIRD_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC_FORTH_QUARTER + ")" + REGEX_OR
                + "(" + CENTURY_ARABIC + ")"
            + ")";
    private static final String CENTURY_ARABIC_INTERVAL = CENTURY_ARABIC_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + CENTURY_ARABIC_OPTIONS + REGEX_OR
                + AGES_ARABIC_NOTATION
            + ")";

    // MILLENNIUM TIME PERIOD (ROMAN NOTATION)
    private static final String MILLENNIUM_ROMAN = MILLENNIUM_NOTATION + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String MILLENNIUM_ROMAN_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ROMAN;
    private static final String MILLENNIUM_ROMAN_OPTIONS =
            "("
                + "(" + MILLENNIUM_ROMAN_FIRST_HALF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_SECOND_HALF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_MIDDLE_OF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_FIRST_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_SECOND_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_THIRD_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_FORTH_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN + ")"
            + ")";
    private static final String MILLENNIUM_ROMAN_INTERVAL = MILLENNIUM_ROMAN_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + MILLENNIUM_ROMAN_OPTIONS + REGEX_OR
                + AGES_ROMAN_NOTATION
            + ")";

    // MILLENNIUM TIME PERIOD (ARABIC NOTATION)
    private static final String MILLENNIUM_ARABIC = MILLENNIUM_NOTATION + REGEX_PUNCTUATION_UNLIMITED + AGES_ARABIC_NOTATION;
    private static final String MILLENNIUM_ARABIC_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + MILLENNIUM_ARABIC;
    private static final String MILLENNIUM_ARABIC_OPTIONS =
            "("
                + "(" + MILLENNIUM_ARABIC_FIRST_HALF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_SECOND_HALF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_MIDDLE_OF + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_FIRST_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_SECOND_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_THIRD_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC_FORTH_QUARTER + ")" + REGEX_OR
                + "(" + MILLENNIUM_ARABIC + ")"
            + ")";
    private static final String MILLENNIUM_ARABIC_INTERVAL = MILLENNIUM_ARABIC_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + MILLENNIUM_ARABIC_OPTIONS + REGEX_OR
                + AGES_ARABIC_NOTATION
            + ")";

    // OTHER TIME PERIOD (ROMAN NOTATION)
    // Treating cases as "1/2 xix", "2/2 vi a. chr." etc. (there is missing the "century" word)
    private static final String OTHER_CENTURY_ROMAN_FIRST_HALF = FIRST_HALF + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_SECOND_HALF = SECOND_HALF + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_MIDDLE_OF = MIDDLE_OF + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_FIRST_QUARTER = FIRST_QUARTER + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_SECOND_QUARTER = SECOND_QUARTER + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_THIRD_QUARTER = THIRD_QUARTER + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    private static final String OTHER_CENTURY_ROMAN_FORTH_QUARTER = FORTH_QUARTER + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
    public static final String OTHER_CENTURY_ROMAN_OPTIONS =
            "("
                + "(" + OTHER_CENTURY_ROMAN_FIRST_HALF + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_SECOND_HALF + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_MIDDLE_OF + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_FIRST_QUARTER + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_SECOND_QUARTER + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_THIRD_QUARTER + ")"
                + REGEX_OR + "(" + OTHER_CENTURY_ROMAN_FORTH_QUARTER + ")"
                    // we can not treat the case of simple ages because it's not possible
                    // to handle cases as "6 mai, v leat 7230 (1722)", "grupa a iv-a" etc.
//                + REGEX_OR + "(" + TEXT_START + AGES_ROMAN_NOTATION + TEXT_END + ")"
            + ")";
    public static final String OTHER_CENTURY_ROMAN_INTERVAL = CASE_INSENSITIVE
            + "("
                + OTHER_CENTURY_ROMAN_OPTIONS
                + REGEX_INTERVAL_DELIMITER
                + "(" + OTHER_CENTURY_ROMAN_OPTIONS + REGEX_OR + AGES_ROMAN_NOTATION + ")"
            + ")";

    public static final String CENTURY_OPTIONS = CASE_INSENSITIVE
            + "("
                + "(" + CENTURY_ARABIC_OPTIONS + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_OPTIONS + ")"
            + ")";

    public static final String MILLENNIUM_OPTIONS = CASE_INSENSITIVE
            + "("
                + "(" + MILLENNIUM_ARABIC_OPTIONS + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_OPTIONS + ")"
            + ")";

    public static final String CENTURY_INTERVAL = CASE_INSENSITIVE
            + "("
                + "(" + CENTURY_ARABIC_INTERVAL + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_INTERVAL + ")"
            + ")";

    public static final String MILLENNIUM_INTERVAL = CASE_INSENSITIVE
            + "("
                + "(" + MILLENNIUM_ARABIC_INTERVAL + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_INTERVAL + ")"
            + ")";
}
