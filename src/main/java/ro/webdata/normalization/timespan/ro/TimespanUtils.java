package ro.webdata.normalization.timespan.ro;

import org.apache.commons.lang3.StringUtils;
import ro.webdata.echo.commons.Collection;
import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.model.*;
import ro.webdata.normalization.timespan.ro.model.date.DateModel;
import ro.webdata.normalization.timespan.ro.model.date.LongDateModel;
import ro.webdata.normalization.timespan.ro.model.date.ShortDateModel;
import ro.webdata.normalization.timespan.ro.model.imprecise.DatelessModel;
import ro.webdata.normalization.timespan.ro.model.imprecise.InaccurateYearModel;
import ro.webdata.normalization.timespan.ro.model.timePeriod.CenturyModel;
import ro.webdata.normalization.timespan.ro.model.timePeriod.MillenniumModel;
import ro.webdata.normalization.timespan.ro.regex.AgeRegex;
import ro.webdata.normalization.timespan.ro.regex.TimePeriodRegex;
import ro.webdata.normalization.timespan.ro.regex.UnknownRegex;
import ro.webdata.normalization.timespan.ro.regex.YearRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;
import ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.DatelessRegex;
import ro.webdata.normalization.timespan.ro.regex.imprecise.InaccurateYearRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimespanUtils {
    private TimespanUtils() {}

    /**
     * TODO: doc<br/>
     * In the matching process the first matched value need to be the interval type,
     * followed by the ordinal values, respecting the following order:
     * <ol>
     *     <li>Map every unknown value in order to clear the list by junk elements</li>
     *     <li>Map every date-like value</li>
     *     <li>Map every century and millennium age-like value</li>
     *     <li>Map every epoch-like value</li>
     * </ol>
     * @param original The original value taken from "lido:displayDate" record
     */
    public static TimespanModel prepareTimespanModel(String original) {
        String value = StringUtils.stripAccents(original);
        TimespanModel timespanModel = new TimespanModel(value);
        updateMatchedValues(original, timespanModel, UnknownRegex.UNKNOWN, TimespanType.UNKNOWN);

        updateMatchedValues(original, timespanModel, DateRegex.DATE_DMY_INTERVAL, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, DateRegex.DATE_YMD_INTERVAL, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, ShortDateRegex.DATE_MY_INTERVAL, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, DateRegex.DATE_DMY_OPTIONS, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, DateRegex.DATE_YMD_OPTIONS, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, ShortDateRegex.DATE_MY_OPTIONS, TimespanType.DATE);
        updateMatchedValues(original, timespanModel, LongDateRegex.LONG_DATE_OPTIONS, TimespanType.DATE);

        updateMatchedValues(original, timespanModel, TimePeriodRegex.CENTURY_INTERVAL, TimespanType.CENTURY);
        updateMatchedValues(original, timespanModel, TimePeriodRegex.CENTURY_OPTIONS, TimespanType.CENTURY);
        updateMatchedValues(original, timespanModel, TimePeriodRegex.MILLENNIUM_INTERVAL, TimespanType.MILLENNIUM);
        updateMatchedValues(original, timespanModel, TimePeriodRegex.MILLENNIUM_OPTIONS, TimespanType.MILLENNIUM);
        updateMatchedValues(original, timespanModel, TimePeriodRegex.OTHER_CENTURY_ROMAN_INTERVAL, TimespanType.CENTURY);
        updateMatchedValues(original, timespanModel, TimePeriodRegex.OTHER_CENTURY_ROMAN_OPTIONS, TimespanType.CENTURY);
        for (int i = 0; i < AgeRegex.AGE_OPTIONS.length; i++) {
            updateMatchedValues(original, timespanModel, AgeRegex.AGE_OPTIONS[i], TimespanType.EPOCH);
        }

        updateMatchedValues(original, timespanModel, DatelessRegex.DATELESS, TimespanType.UNKNOWN);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.AFTER_INTERVAL, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.BEFORE_INTERVAL, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.APPROX_AGES_INTERVAL, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.AFTER, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.BEFORE, TimespanType.YEAR);

        updateMatchedValues(original, timespanModel, YearRegex.YEAR_INTERVAL, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, InaccurateYearRegex.APPROX_AGES_OPTIONS, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL, TimespanType.YEAR);
        updateMatchedValues(original, timespanModel, YearRegex.YEAR_OPTIONS, TimespanType.YEAR);
        // This call need to be made after all the years processing !!!
        updateMatchedValues(original, timespanModel, YearRegex.UNKNOWN_YEARS, TimespanType.UNKNOWN);

        return timespanModel;
    }

    //TODO: "1/2 mil. 5 - sec. I al mil. 4 a.Chr."
    //TODO: "2 a.chr - 14 p.chr"
    private static void updateMatchedValues(String original, TimespanModel timespanModel, String regex, String timespanType) {
        String initialValue = timespanModel.getResidualValue();
        initialValue = TimeSanitizeUtils.sanitizeValue(initialValue, regex);
        String residualValue = initialValue
                .replaceAll(regex, Const.EMPTY_VALUE_PLACEHOLDER);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(initialValue);

        while (matcher.find()) {
            String group = matcher.group();
            TimePeriodModel timePeriod = prepareTimePeriodModel(original, TimeUtils.normalizeChristumNotation(group), regex);
            String matchedItems = timePeriod.toString();

            Map<String, DBpediaModel> edges = TimespanUtils.prepareEdges(timePeriod, timespanType);
            timespanModel.addDbpediaEdges(edges);

            if (!matchedItems.equals(group) && matchedItems.length() > 0) {
                String[] matchedList = matchedItems.split(Collection.STRING_LIST_SEPARATOR);
                timespanModel.addDBpediaItems(matchedList, timespanType);
            } else if (matchedItems.equals(group)) {
                System.err.println("The following group has not been processed: \"" + group + "\"");
            }
        }

        timespanModel.setResidualValue(residualValue);
    }

    private static Map<String, DBpediaModel> prepareEdges(TimePeriodModel timePeriod, String timespanType) {
        Map<String, DBpediaModel> edges = new HashMap<>();
        DBpediaModel start = new DBpediaModel(timePeriod.toDBpediaStartUri(timespanType), timespanType);
        DBpediaModel end = new DBpediaModel(timePeriod.toDBpediaEndUri(timespanType), timespanType);

        edges.put("start", start);
        edges.put("end", end);

        return edges;
    }

    /**
     * Ensure the right format for date-like, year-like, centuries and millenniums values
     * @param original The original value
     * @param value The value subjected to the replacement process (part of the original value)
     * @param regex A regular expression
     * @return The formatted value
     */
    private static TimePeriodModel prepareTimePeriodModel(String original, String value, String regex) {
        TimePeriodModel prepared = prepareAges(original, value, regex);

        if (prepared == null) {
            prepared = prepareDateTime(original, value, regex);
        }

        if (prepared == null) {
            prepared = preparePeriod(original, value, regex);
        }

        return prepared;
    }

    /**
     * Ensure the right format for year-like value
     * @param original The original value
     * @param value The value subjected to the replacement process (part of the original value)
     * @param regex A regular expression
     * @return The formatted value
     */
    private static TimePeriodModel prepareAges(String original, String value, String regex) {
        switch (regex) {
            case InaccurateYearRegex.AFTER:
            case InaccurateYearRegex.AFTER_INTERVAL:
            case InaccurateYearRegex.APPROX_AGES_INTERVAL:
            case InaccurateYearRegex.APPROX_AGES_OPTIONS:
            case InaccurateYearRegex.BEFORE:
            case InaccurateYearRegex.BEFORE_INTERVAL:
                return new InaccurateYearModel(original, value);
            case DatelessRegex.DATELESS:
            case YearRegex.UNKNOWN_YEARS:
            case UnknownRegex.UNKNOWN:
                return new DatelessModel(original, value);
            case YearRegex.YEAR_INTERVAL:
            case YearRegex.YEAR_3_4_DIGITS_SPECIAL_INTERVAL:
            case YearRegex.YEAR_OPTIONS:
                return new YearModel(original, value);
            default:
                return null;
        }
    }

    /**
     * Ensure the right format for date-like value
     * @param original The original value
     * @param value The value subjected to the replacement process (part of the original value)
     * @param regex A regular expression
     * @return The formatted value
     */
    private static TimePeriodModel prepareDateTime(String original, String value, String regex) {
        switch (regex) {
            case DateRegex.DATE_DMY_INTERVAL:
            case DateRegex.DATE_DMY_OPTIONS:
                return new DateModel(original, value, TimeUtils.DMY_PLACEHOLDER);
            case DateRegex.DATE_YMD_INTERVAL:
            case DateRegex.DATE_YMD_OPTIONS:
                return new DateModel(original, value, TimeUtils.YMD_PLACEHOLDER);
            case ShortDateRegex.DATE_MY_INTERVAL:
            case ShortDateRegex.DATE_MY_OPTIONS:
                return new ShortDateModel(original, value, TimeUtils.MY_PLACEHOLDER);
            case LongDateRegex.LONG_DATE_OPTIONS:
                return new LongDateModel(original, value);
            default:
                return null;
        }
    }

    /**
     * Ensure the right format for centuries and millenniums
     * @param original The original value
     * @param value The value subjected to the replacement process (part of the original value)
     * @param regex A regular expression
     * @return The list of formatted values
     */
    private static TimePeriodModel preparePeriod(String original, String value, String regex) {
        switch (regex) {
            case TimePeriodRegex.CENTURY_INTERVAL:
            case TimePeriodRegex.CENTURY_OPTIONS:
            case TimePeriodRegex.OTHER_CENTURY_ROMAN_INTERVAL:
            case TimePeriodRegex.OTHER_CENTURY_ROMAN_OPTIONS:
                return new CenturyModel(original, value);
            case TimePeriodRegex.MILLENNIUM_INTERVAL:
            case TimePeriodRegex.MILLENNIUM_OPTIONS:
                return new MillenniumModel(original, value);
            case AgeRegex.AURIGNACIAN_CULTURE:
            case AgeRegex.BRONZE_AGE:
            case AgeRegex.CHALCOLITHIC_AGE:
            case AgeRegex.FRENCH_CONSULATE_AGE:
            case AgeRegex.HALLSTATT_CULTURE:
            case AgeRegex.INTERWAR_PERIOD:
            case AgeRegex.MESOLITHIC_AGE:
            case AgeRegex.MIDDLE_AGES:
            case AgeRegex.MODERN_AGES:
            case AgeRegex.NEOLITHIC_AGE:
            case AgeRegex.NERVA_ANTONINE_DYNASTY:
            case AgeRegex.PLEISTOCENE_AGE:
            case AgeRegex.PTOLEMAIC_DYNASTY:
            case AgeRegex.RENAISSANCE:
            case AgeRegex.ROMAN_EMPIRE_AGE:
            case AgeRegex.WW_I_PERIOD:
            case AgeRegex.WW_II_PERIOD:
                return new AgeModel(original, value, regex);
            default:
                return null;
        }
    }
}
