package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

/**
 * E.g.: "aprox. 1900"
 */
// TODO: find a way to store the detail for inaccurate time periods (after, before, approx.)
public class InaccurateYearModel extends TimePeriodModel {
    private static final String REGEX_NON_DIGIT = "[^\\d]";

    public InaccurateYearModel(String original, String value, boolean historicalOnly) {
        setDateModel(original, value, historicalOnly);
    }

    private void setDateModel(String original, String value, boolean historicalOnly) {
        String[] intervalValues = value.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], true);

            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);
            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);

            setDate(original, endValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, startValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(original, value, value, true);

            String preparedValue = TimeUtils.clearChristumNotation(value);

            setDate(original, preparedValue, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, preparedValue, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private void setDate(String original, String value, String position, boolean historicalOnly) {
        String year = value
                .replaceAll(REGEX_NON_DIGIT, Const.EMPTY_VALUE_PLACEHOLDER);

        setMillennium(original, year, position, historicalOnly);
        setCentury(original, year, position, historicalOnly);
        setYear(original, year, position, historicalOnly);
    }
}
