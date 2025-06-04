package ro.webdata.normalization.timespan.ro.regex.imprecise;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

public class DatelessRegex {
    private DatelessRegex() {}

    /**
     * E.g.:
     * <ul>
     *     <li>"model 1850"</li>
     * </ul>
     */
    private static final String MODEL_X = "(" + TEXT_START
                + "model\\s*\\d{4}"
            + ")" + TEXT_END;

    /**
     * E.g.:
     * <ul>
     *     <li>"nedatat (1897)"</li>
     *     <li>"1910 (nedatat)"</li>
     *     <li>"nedatabil"</li>
     *     <li>"nu are"</li>
     * </ul>
     */
    private static final String UNDATED = "("
                + ".*("
                    + "nedatat"+ REGEX_OR
                    + "nedatabil" + REGEX_OR
                    + "nu\\s*are"
                + ").*"
            + ")";

    /**
     * E.g.:
     * <ul>
     *     <li>"f.a. octombrie 29"; "f.an"</li>
     * </ul>
     */
    private static final String WITHOUT_AGE = "("
                + ".*("
                    + "(fara\\s*an)" + REGEX_OR
                    + "(f\\.\\s*an)" + REGEX_OR
                    + "(f\\.a)"
                + ")"
            + ")";

    /**
     * E.g.:
     * <ul>
     *     <li>"fara data"</li>
     *     <li>"1861 f.d"</li>
     * </ul>
     */
    private static final String WITHOUT_DATE = "("
                + "("
                    + "(fara\\s*data)" + REGEX_OR
                    + "(f\\.\\s*data)" + REGEX_OR
                    + "(f\\.d)"
                + ")"
            + ").*";

    /**
     * E.g.:
     * <ul>
     *     <li>"f.a. octombrie 29"; "f.an"</li>
     *     <li>"fara data"</li>
     *     <li>"1861 f.d"</li>
     * </ul>
     */
    public static final String DATELESS = CASE_INSENSITIVE
            + "("
                + WITHOUT_AGE + REGEX_OR
                + WITHOUT_DATE
            + ")";

    /**
     * E.g.:
     * <ul>
     *     <li>"model 1850"</li>
     * </ul>
     */
    public static final String DATELESS_MODEL_X = CASE_INSENSITIVE + MODEL_X;

    /**
     * E.g.:
     * <ul>
     *     <li>"nedatat (1897)"</li>
     *     <li>"1910 (nedatat)"</li>
     *     <li>"nedatabil"</li>
     *     <li>"nu are"</li>
     * </ul>
     */
    public static final String DATELESS_UNDATED = CASE_INSENSITIVE + UNDATED;
}
