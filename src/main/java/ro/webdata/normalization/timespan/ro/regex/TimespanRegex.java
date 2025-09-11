package ro.webdata.normalization.timespan.ro.regex;

/**
 * By using the <b>[?<=sentence]</b> construction, it will be matched
 * any text after "sentence" ("sentence" is a word as example).
 */
public class TimespanRegex {
    private TimespanRegex() {}

    public static final String ANY_WORDS = "[\\wăâîşșţțĂÂÎŞȘŢȚ]*";
    public static final String REGEX_OR = "|";
    public static final String CASE_INSENSITIVE = "(?iu)";
    public static final String AD_BC_OPTIONAL = "(\\s*" + TimespanRegex.CHRISTUM_NOTATION + ")?";

    public static final String REGEX_PUNCTUATION = "[\\.,;\\?!\\-\\s]";
    public static final String REGEX_PUNCTUATION_UNLIMITED = REGEX_PUNCTUATION + "*";
    // The separator must always be of the shape "(\\s+-\\s+)"
    public static final String REGEX_INTERVAL_DELIMITER = "\\s*(?:-|–)\\s*";
    public static final String REGEX_INTERVAL_CONJUNCTION = "\\s*(?:-|–|([sşș]i))\\s*";
    public static final String REGEX_INTERVAL_PREFIX = "(?:[iî]ntre" + REGEX_OR + "[iî]n\\s*interval(?:ul|u)?)\\s*";
    public static final String REGEX_DATE_SEPARATOR = "[\\./\\-\\s]+";
    /**
     * Regex for marking the start of the text
     */
    public static final String TEXT_START =
            "("
                + "?<=" + "("
                    + "^" + REGEX_OR + "\\A" + REGEX_OR + "[\\.,;\\?!\\-(\\[= ]+"
                + ")"
            + ")";
    /**
     * Regex for marking the end of the text
     */
    public static final String TEXT_END =
            "("
                + "?=" + "("
                    + "$" + REGEX_OR + "\\z" + REGEX_OR + "[\\.,;\\?!\\-)\\] ]+"
                + ")"
            + ")";
    /**
     * Regex for all possible values for Christum notation<br/>
     * E.g.: "ch"; "ch."; "chr"; "chr."; "hr"; "hr."
     */
    private static final String REGEX_CHRISTUM = "(ch[r]?|hr|c)[\\. ]*";

    // Anno Domini (After Christ)
    public static final String AGE_AD = TEXT_START
            + CASE_INSENSITIVE
            + "("
                + "(" + "e[\\.]?n[\\.]?" + ")" + REGEX_OR
                + "(" + "[dp][\\. ]*" + REGEX_CHRISTUM + ")"
            + ")" + TEXT_END;

    // Before Christ
    public static final String AGE_BC = TEXT_START
            + CASE_INSENSITIVE
            + "("
                + "(" + "[iî][\\.]?e[\\.]?n[\\.]?" + ")" + REGEX_OR
                + "(" + "[abiî][\\. ]*" + REGEX_CHRISTUM + ")"
            + ")" + TEXT_END;
    public static final String CHRISTUM_NOTATION = "(" + AGE_AD + REGEX_OR + AGE_BC + ")";

    public static final String MONTHS_DIGITS = "(?:0?(?:1|2|3|4|5|6|7|8|9))|10|11|12";
    public static final String MONTHS_RO =
            "("
                + "ianuarie|fe[bv]ruarie|martie|aprilie|mai|iu[mn]ie|iulie|august|septembrie|[o0]ctombrie|noiembrie|decembrie"
                + REGEX_OR
                + "(ian|feb(r)?|mart|apr|iun|iul|aug|sept|[o0]ct|noi|dec)\\."
                + REGEX_OR
                + "noimbrie|decembre"
            + ")";
    public static final String MONTHS_EN =
            "("
                + "january|february|march|april|may|june|july|august|september|october|november|december"
                + REGEX_OR
                + "(jan|feb|apr|jun|jul|aug|sep|oct|nov|dec)\\."
            + ")";
    public static final String MONTHS = "(" + MONTHS_RO + REGEX_OR + MONTHS_EN + REGEX_OR + MONTHS_DIGITS + ")";

    public static final String AGES_GROUP_SUFFIX = "([- ]*lea)?";
    public static final String AGES_ARABIC_GROUP = "(" + TEXT_START + "\\d+" + TEXT_END + ")";
    public static final String AGES_ARABIC_NOTATION =
            "("
                + AGES_ARABIC_GROUP
                + AGES_GROUP_SUFFIX
                + "("
                    + REGEX_PUNCTUATION_UNLIMITED + CHRISTUM_NOTATION
                + ")?"
            + ")";
    public static final String AGES_ROMAN_GROUP =
            "("
                + TEXT_START + "[ivxlcdm]+" + TEXT_END
            + ")";
    public static final String AGES_ROMAN_NOTATION =
            "("
                + AGES_ROMAN_GROUP
                + AGES_GROUP_SUFFIX
                + "("
                    + REGEX_PUNCTUATION_UNLIMITED + CHRISTUM_NOTATION
                + ")?"
            + ")";

    public static final String AGES_NOTATIONS = "(" + AGES_ROMAN_NOTATION + REGEX_OR + AGES_ARABIC_NOTATION + ")";

    public static final String ARTICLE_AL = "(?:al[\\.\\s]*)?";
    private static final String START = "((?:[iî]nceput(?:u(?:l)?|ului)?|[iî]nc\\.?)(?:\\s+de)?)?";
    private static final String END = "((?:sf[aâ]r[sșş]it(?:u(?:l)?)?|sf[\\.\\s]{0,6})(?:\\s+de)?)?";

    // E.g.: "sfârșitul sec. xi-începutul sec. xiii p. chr"
    public static final String START_END = END + START;
    public static final String CENTURY_LABEL = "(" + START_END +  "\\s*(?:(secol|secoi)(?:ele|ului|ul)?|sec)[\\.\\s]*" + ARTICLE_AL + ")";
    public static final String MILLENNIUM_LABEL = "(" + START_END + "\\s*(?:mileni(?:ile|ului|ul)?|mil)[\\.\\s]*" + ARTICLE_AL + ")";
    public static final String YEAR_LABEL = "(" + START_END + "\\s*(?:ani+(?:lor)?|an(?:ului|ulu|ul|u)?)\\s*" + ARTICLE_AL + ")";

    private static final String HALF = "jum([aă]tate(a)?)?";
    private static final String FIRST_HALF_STRING_REGEX = "(" + "prim[a]*[\\. ]+(" + HALF + "|part)" + ")";
    private static final String SECOND_HALF_STRING_REGEX =  "(" + "a\\s+(doua|(ii[-a]*))[\\. ]+(" + HALF + "|part)" + ANY_WORDS + ")";
    private static final String REGEX_A_AL_POSTFIX = "(" + ANY_WORDS + "[\\.]*([\\. ]+(a|al))*" + ")";

    public static final String FIRST_HALF =
            "("
                + TEXT_START + "("
                    + "(1/2)" + REGEX_OR
                    + "(½)" + REGEX_OR
                    + "(" + FIRST_HALF_STRING_REGEX + REGEX_A_AL_POSTFIX + ")"
                + ")" + TEXT_END
            + ")";
    public static final String SECOND_HALF =
            "("
                + TEXT_START + "("
                    + "(2/2)" + REGEX_OR
                    + "(" + SECOND_HALF_STRING_REGEX + REGEX_A_AL_POSTFIX + ")"
                + ")" + TEXT_END
            + ")";
    public static final String MIDDLE_OF =
            "("
                + TEXT_START + "(" + HALF + "|(mij" + ANY_WORDS + ")|mj\\.)" + TEXT_END
            + ")";

    /**
     * First Quarter = Beginning of...
     * */
    public static final String FIRST_QUARTER =
            "("
                + TEXT_START + "("
                    + "(1/4)" + REGEX_OR
                    + "(¼)" + REGEX_OR
                    + "(" + "([iî]nc" + ANY_WORDS + "[\\. ]*)" + "(de)?" + ")" + REGEX_OR
                    + "(" + "primul\\s+sfert" + "(\\s+a[l]?)?" + ")" + REGEX_OR
                    + "(" + "prima treime a" + ")"
                + ")" + TEXT_END
            + ")";
    public static final String SECOND_QUARTER =
            "("
                + TEXT_START + "("
                    + "(2/4)" + REGEX_OR
                    + "(al doile[a]? sfert al)"
                + ")" + TEXT_END
            + ")";
    public static final String THIRD_QUARTER =
            "("
                + TEXT_START + "("
                    + "(3/4)" + REGEX_OR
                    + "(¾)" + REGEX_OR
                    + "(al treilea sfert al)"
                + ")" + TEXT_END
            + ")";
    /**
     * Last Quarter = End of...
     * */
    public static final String FORTH_QUARTER =
            "("
                + TEXT_START + "("
                    + "(4/4)" + REGEX_OR
                    + "(" + "(ultimul\\s+sfert)" + "(\\s+(a[l]?|de)*)?" + ")"
                + ")"
            + ")";
}
