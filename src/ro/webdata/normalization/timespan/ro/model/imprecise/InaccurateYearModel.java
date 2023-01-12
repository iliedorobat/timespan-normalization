package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

/**
 * E.g.: "aprox. 1900"
 */
// TODO: find a way to store the detail for inaccurate time periods (after, before, approx.)
public class InaccurateYearModel extends TimePeriodModel {
    private static final String REGEX_NON_DIGIT = "[^\\d]";

    public InaccurateYearModel(TimespanModel timespanModel, String original, String value) {
        setDateModel(timespanModel, original, value);
    }

    private void setDateModel(TimespanModel timespanModel, String original, String value) {
        String[] intervalValues = value.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], true);

            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);
            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);

            setDate(timespanModel, original, endValue, TimeUtils.END_PLACEHOLDER);
            setDate(timespanModel, original, startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(original, value, value, true);

            String preparedValue = TimeUtils.clearChristumNotation(value);

            setDate(timespanModel, original, preparedValue, TimeUtils.END_PLACEHOLDER);
            setDate(timespanModel, original, preparedValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setDate(TimespanModel timespanModel, String original, String value, String position) {
        String year = value
                .replaceAll(REGEX_NON_DIGIT, Const.EMPTY_VALUE_PLACEHOLDER);

        setMillennium(timespanModel, original, year, position);
        setCentury(timespanModel, original, year, position);
        setYear(timespanModel, original, year, position);
    }
}
