package ro.webdata.normalization.timespan.ro.regex.imprecise;

import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;
import static ro.webdata.normalization.timespan.ro.regex.YearRegex.YEAR_NOTATION;

public class InaccurateYearRegex {
    private InaccurateYearRegex() {}

    public static final String APPROX_NOTATION = TEXT_START
            + "("
                + "catre" + REGEX_OR
                + "probabil" + REGEX_OR
                + "aprox[\\.]*" + REGEX_OR
                + "aproximativ(\\s*anii)?" + REGEX_OR
                + "cca[\\.]*" + REGEX_OR
                + "c[a]?[\\.]?" + REGEX_OR
                + "circa"
            + ")\\s*";
    private static final String APPROX_AGES_GROUP =
            "("
                + "("
                    + APPROX_NOTATION + "\\d+"
                + ")"
                + "(" + REGEX_PUNCTUATION_UNLIMITED + AD_BC_OPTIONAL + ")*"
            + ")";

    public static final String APPROX_AGES_OPTIONS = TEXT_START + APPROX_AGES_GROUP + TEXT_END;
    private static final String APPROX_AGES_INTERVAL_1 = TEXT_START
            + APPROX_AGES_OPTIONS
            + REGEX_INTERVAL_DELIMITER
            + "("
                // E.g.: "[c. 250-c. 225 a.chr.]"
                + APPROX_AGES_OPTIONS + REGEX_OR
                // E.g.: "[c. 260-230]"
                + "(" + YEAR_NOTATION + ")"
            + ")"
            + TEXT_END;
    // E.g.: "281 a.chr. - cca 200 a.chr."
    private static final String APPROX_AGES_INTERVAL_2 = TEXT_START
            + "(" + YEAR_NOTATION + ")"
            + REGEX_INTERVAL_DELIMITER
            + "(" + APPROX_AGES_OPTIONS + ")"
            + TEXT_END;

    public static final String APPROX_AGES_INTERVAL = APPROX_AGES_INTERVAL_1 + REGEX_OR + APPROX_AGES_INTERVAL_2;

    private static final String DATE = "("
                + DateRegex.DATE_DMY_OPTIONS + REGEX_OR
                + DateRegex.DATE_YMD_OPTIONS  + REGEX_OR
                + "("
                    + TEXT_START + "\\d{1,4}" + AD_BC_OPTIONAL + TEXT_END
                + ")"
            + ")";

    /**
     * E.g.:
     * <ul>
     *     <li>"după 1628"</li>
     *     <li>"dupa 29 aprilie 1616"</li>
     *     <li>"post 330-320 a.chr."</li>
     *     <li>"postum 161 p.chr."</li>
     * </ul>
     */
    public static final String AFTER = TEXT_START + "("
                + "("
                    + "dupa" + REGEX_OR
                    + "post" + REGEX_OR
                    + "postum"
                + ")" + "\\s*"
                + DATE
            + ")" + TEXT_END;

    public static final String AFTER_INTERVAL =
            AFTER + REGEX_INTERVAL_DELIMITER + DATE;

    /**
     * E.g.:
     * <ul>
     *     <li>"ante 1801"</li>
     *     <li>"anterior lui 1890 (data mortii mesterului)"</li>
     *     <li>"inainte de 211 a.chr."</li>
     * </ul>
     */
    public static final String BEFORE = TEXT_START + "("
                + "("
                    + "ante" + REGEX_OR
                    + "anterior\\s*lui" + REGEX_OR
                    + "inainte\\s*de"
                + ")" + "\\s*"
                + DATE
            + ")" + TEXT_END;

    public static final String BEFORE_INTERVAL =
            BEFORE + REGEX_INTERVAL_DELIMITER + DATE;
}
