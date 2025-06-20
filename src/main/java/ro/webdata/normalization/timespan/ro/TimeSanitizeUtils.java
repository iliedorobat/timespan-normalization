package ro.webdata.normalization.timespan.ro;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;

public class TimeSanitizeUtils {
    private TimeSanitizeUtils() {}

    /**
     * Sanitize some values that appear rarely and a regex operation
     * would be time-consuming
     * @param value The input value
     * @return The sanitized value
     */
    public static String sanitizeValue(String value) {
        String sanitized = sanitizeDateTime(value);
        sanitized = sanitizeAges(sanitized);
        sanitized = sanitizeTimePeriods(sanitized);

        return sanitized;
    }

    /**
     * Clean value by junks that could be interpreted by other regexes<br/>
     * E.g.: "anul 13=1800/1801" will lead to "anul 13=" junk value.
     * This junk value could be interpreted by another regex as being
     * a year, which is wrong.
     * @param value The input value
     * @param regex The related regular expression
     * @return The cleaned value
     */
    public static String clearJunks(String value, String regex) {
        // Avoid adding junks that could be interpreted by other regexes.
        // E.g.: "anul 13=1800/1801" will lead to "anul 13=" junk value
        if (regex != null && regex.equals(YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL)) {
            return value.replaceAll(
                    YearRegex.YEAR_3_4_DIGITS_SPECIAL_PREFIX,
                    Const.EMPTY_VALUE_PLACEHOLDER
            );
        }

        return value;
    }

    /**
     * Sanitize date-like values that appear rarely and a regex operation
     * would be time-consuming
     * @param value The input value
     * @return The sanitized value
     */
    private static String sanitizeDateTime(String value) {
        switch (value) {
            case "17 nov. 375-9 aug. 378 a.chr.":
                return "17 nov. 375 - 9 aug. 378 a.chr.";
            case "[11-13 martie] 1528":
                return "11 martie 1528 - 13 martie 1528";
            case "6 octombrie1904":
                return "6 octombrie 1904";
            case "30 mai și 5 august 1796":
                return "30 mai 1796; 5 august 1796";
            case "1861septembrie 25":
                return "25 septembrie 1861";
            case "1908 martie 27-28":
                return "27 martie 1908 - 28 martie 1908";
            case "1834, 1 - 10 aprilie":
                return "1 aprilie 1834 - 10 aprilie 1834";
            default:
                return value;
        }
    }

    /**
     * Sanitize year-like values that appear rarely and a regex operation
     * would be time-consuming
     * @param value The input value
     * @return The sanitized value
     */
    private static String sanitizeAges(String value) {
        if (value.equals("1880 (proprietarul avea 90 de ani în 1970)"))
            return "1880";

        return value;
    }

    /**
     * Sanitize centuries and millenniums values that rarely appear
     * and a regex operation would be time-consuming
     * @param value The input value
     * @return The sanitized value
     */
    private static String sanitizeTimePeriods(String value) {
        switch (value) {
            case "15(6)3":
                return "1563";
            case "1884 martie 28/aprilie 09":
                return "1884";
            case "octombrie 23, 1777":
                return "23 octombrie 1777";
            case "a doua jumatate a sec. i a.chr. (-43 - -29); a doua jum. a sec.xix (montură inel)":
                return "a doua jumatate a sec. i a.chr. (43 - 29 a.chr.); a doua jum. a sec.xix (montură inel)";
            case "instituit în decembrie 1915 - desființat în 1973":
                return "1915 - 1973";
            case "prima jumătate a secolului xviii (rest de datare 174...)":
                return "prima jumătate a secolului xviii";
            case "sec. xviii - xix 18(40)":
                return "sec. xviii - xix";
            case "281-222 (232?) p. chr.":
                return "281-222 p. chr.";
            case "0803":
                return "unknown";
            case "13 (1805)":
                return "1805";
            case "1/4 mil. 5 - sec. i al mil. 4 a.chr.":
                return "1/4 mil. 5 - mil. 4 a.chr.";
            case "2/2 mil. 5 - sec. i al mil. 4 a.chr.":
                return "2/2 mil. 5 - mil. 4 a.chr.";
            case "1/2 mil. 5 - sec. i al mil. 4 a.chr.":
                return "1/2 mil. 5 - mil. 4 a.chr";
            case "34 sec. xx":
                return "sec. xx";
            case "i p. chr.":
                return "sec. i p.ch.";
            case "i a. chr.-i p. chr.":
                return "sec. i a.ch. - sec. i p.ch.";
            case "ii-i a.chr.":
                return "sec. ii - sec. i a.ch.";
            case "iii i. hr.":
                return "sec. iii a.ch.";
            case "iv - ii i. hr.":
                return "sec. iv - sec. ii a.ch.";
            case "xiii i.e.n":
                return "sec. xiii i.e.n";
            case "xvii":
            case "s: xvii":
                return "sec. xvii";
            case "prima jum. se. xix":
                return "prima jum. sec. xix";
            case "s:20; 1/4":
            case "s: xx; a: 1/4":
            case "s: xx; 1/4":
                return "1/4 sec. xx";
            case "s: xviii; 1/4":
                return "1/4 sec. xviii";
            case "se. iv-vp.ch.":
                return "sec. iv - sec. v p.ch.";
            case "sec. va. chr.":
                return "sec. v a.chr.";
            case "sec.vp.ch.":
                return "sec. v p.ch.";
            case "secolele xix/xx":
                return "sec. xix - sec. xx";
            case "secolul iv/v":
                return "sec. iv - sec. v";
            case "sex. xvii - a ii jumatate":
                return "a doua jumatate a sec. xvii";
            case "xix":
            case "sec: xix":
            case "sex. xix":
            case "secolul al-xix -lea":
                return "sec. xix";
            case "sex. xvii - a ii jumătate":
                return "a doua jumătate a sec. xvii";
            case "sec. xix/2/2":
                return "2/2 sec. xix";
            case "xi - ix a.hr.":
                return "sec. xi - sec. ix a.hr.";
            case "sec. -4 - -2":
                return "sec. 4 - 2 a.ch.";
            case "sec. -7 - -5":
                return "sec. 7 - 5 a.ch.";
            case "inc. sex. xx":
                return "inceputul sec. xx";
            case "3/4 ec. xix":
                return "3/4 sec. xix";
            case "s:19; 4/4":
            case "4/4ec. xix":
            case "4/4 secolul al xix/lea":
                return "4/4 sec. xix";
            case "sfârșitul sexc. xix":
                return "sfârșitul sec. xix";
            case "xx":
            case "anii 30-40 secolul xx":
            case "anii 30 ai secolului xx":
            case "prima jumătate a anului '30":
            case "mijlocul anilor '20":
            case "a doua jumătate a anilor '20":
            case "a doua jumătate a anilor '30":
                return "sec. xx";
            case "mijlocul secolului al doilea a.chr.":
                return "mijlocul sec. ii a.ch.";
            case "mileniile v-iva. chr.":
                return "mileniile v-iv a. chr.";
            case "decembrie 01 fara an":
            case "mesiața dec., dni 10, leat 7156":
            case "mesiața ghen., dni 18, leat 7158":
            case "mesiața iolie, 20 dni, leat 7156":
            case "mesiața iunie, dni 12, v leat 7105 (1597)":
            case "mesiața mr., dni 22, leat 7189":
            case "mesiața oct., dni 28, vleat 7152":
                return "";
            default:
                return value;
        }
    }
}
