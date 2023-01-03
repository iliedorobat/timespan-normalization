package ro.webdata.normalization.timespan.ro.model.imprecise;

import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

//TODO: find a way to store the detail for inaccurate time periods (after, before, approx.)
public class InaccurateYearModel extends TimePeriodModel {
    private static final String REGEX_NON_DIGIT = "[^\\d]";

    public InaccurateYearModel(String value) {
        setDateModel(value);
    }

    private void setDateModel(String value) {
        String[] intervalValues = value.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(intervalValues[0], intervalValues[1]);

            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);
            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);

            setDate(endValue, TimeUtils.END_PLACEHOLDER);
            setDate(startValue, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            String preparedValue = TimeUtils.clearChristumNotation(value);

            setDate(preparedValue, TimeUtils.END_PLACEHOLDER);
            setDate(preparedValue, TimeUtils.START_PLACEHOLDER);
        }
    }

    //TODO: "dupa 29 aprilie 1616"; "dupa 10 mai 1903"
    private void setDate(String value, String position) {
        String year = value
                .replaceAll(REGEX_NON_DIGIT, Const.EMPTY_VALUE_PLACEHOLDER);

        setMillennium(year, position);
        setCentury(year, position);
        setYear(year, position);
    }
}
