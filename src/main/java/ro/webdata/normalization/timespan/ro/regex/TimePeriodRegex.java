package ro.webdata.normalization.timespan.ro.regex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that are stored
 * as ages (centuries and millenniums)
 */
public class TimePeriodRegex {
    private TimePeriodRegex() {}

    // CENTURY TIME PERIOD (ROMAN NOTATION)
    private static final String CENTURY_ROMAN = CENTURY_LABEL + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
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
    private static final String CENTURY_ROMAN_INTERVAL_BASE = CENTURY_ROMAN_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + CENTURY_ROMAN_OPTIONS + REGEX_OR
                + AGES_ROMAN_NOTATION
            + ")";
    private static final String CENTURY_ROMAN_INTERVAL_PREFIXED = REGEX_INTERVAL_PREFIX
            + CENTURY_ROMAN_OPTIONS + REGEX_INTERVAL_CONJUNCTION
            + "("
                + "(" + ARTICLE_AL + AGES_ROMAN_NOTATION + ")" + REGEX_OR
                + CENTURY_ROMAN_OPTIONS
            + ")";

    // CENTURY TIME PERIOD (ARABIC NOTATION)
    private static final String CENTURY_ARABIC = CENTURY_LABEL + REGEX_PUNCTUATION_UNLIMITED + AGES_ARABIC_NOTATION;
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
    private static final String CENTURY_ARABIC_INTERVAL_BASE = CENTURY_ARABIC_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + CENTURY_ARABIC_OPTIONS + REGEX_OR
                + AGES_ARABIC_NOTATION
            + ")";
    private static final String CENTURY_ARABIC_INTERVAL_PREFIXED = REGEX_INTERVAL_PREFIX
            + CENTURY_ARABIC_OPTIONS + REGEX_INTERVAL_CONJUNCTION
            + "("
                + "(" + ARTICLE_AL + AGES_ARABIC_NOTATION + ")" + REGEX_OR
                + CENTURY_ARABIC_OPTIONS
            + ")";

    // MILLENNIUM TIME PERIOD (ROMAN NOTATION)
    private static final String MILLENNIUM_ROMAN = MILLENNIUM_LABEL + REGEX_PUNCTUATION_UNLIMITED + AGES_ROMAN_NOTATION;
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
    private static final String MILLENNIUM_ROMAN_INTERVAL_BASE = MILLENNIUM_ROMAN_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + MILLENNIUM_ROMAN_OPTIONS + REGEX_OR
                + AGES_ROMAN_NOTATION
            + ")";
    private static final String MILLENNIUM_ROMAN_INTERVAL_PREFIXED = REGEX_INTERVAL_PREFIX
            + MILLENNIUM_ROMAN_OPTIONS + REGEX_INTERVAL_CONJUNCTION
            + "("
                + "(" + ARTICLE_AL + AGES_ROMAN_NOTATION + ")" + REGEX_OR
                + MILLENNIUM_ROMAN_OPTIONS
            + ")";

    // MILLENNIUM TIME PERIOD (ARABIC NOTATION)
    private static final String MILLENNIUM_ARABIC = MILLENNIUM_LABEL + REGEX_PUNCTUATION_UNLIMITED + AGES_ARABIC_NOTATION;
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
    private static final String MILLENNIUM_ARABIC_INTERVAL_BASE = MILLENNIUM_ARABIC_OPTIONS + REGEX_INTERVAL_DELIMITER
            + "("
                + MILLENNIUM_ARABIC_OPTIONS + REGEX_OR
                + AGES_ARABIC_NOTATION
            + ")";
    private static final String MILLENNIUM_ARABIC_INTERVAL_PREFIXED = REGEX_INTERVAL_PREFIX
            + MILLENNIUM_ARABIC_OPTIONS + REGEX_INTERVAL_CONJUNCTION
            + "("
                + "(" + ARTICLE_AL + AGES_ARABIC_NOTATION + ")" + REGEX_OR
                + MILLENNIUM_ARABIC_OPTIONS
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

    public static final String CENTURY_INTERVAL_BASE = CASE_INSENSITIVE
            + "("
                + "(" + CENTURY_ARABIC_INTERVAL_BASE + ")" + REGEX_OR
                + "(" + CENTURY_ROMAN_INTERVAL_BASE + ")"
            + ")";

    public static final String CENTURY_INTERVAL_PREFIXED = CASE_INSENSITIVE
        + "("
            + "(" + CENTURY_ARABIC_INTERVAL_PREFIXED + ")" + REGEX_OR
            + "(" + CENTURY_ROMAN_INTERVAL_PREFIXED + ")"
        + ")";

    public static final String MILLENNIUM_INTERVAL_BASE = CASE_INSENSITIVE
            + "("
                + "(" + MILLENNIUM_ARABIC_INTERVAL_BASE + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_INTERVAL_BASE + ")"
            + ")";

    public static final String MILLENNIUM_INTERVAL_PREFIXED = CASE_INSENSITIVE
            + "("
                + "(" + MILLENNIUM_ARABIC_INTERVAL_PREFIXED + ")" + REGEX_OR
                + "(" + MILLENNIUM_ROMAN_INTERVAL_PREFIXED + ")"
            + ")";
}
