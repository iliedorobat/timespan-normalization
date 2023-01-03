package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;

/**
 * Used for date presented as month-year format<br/>
 * E.g.:<br/>
 *      * MY: "octombrie 1639", "ianuarie 632", "septembrie - octombrie 1919"
 */
public class ShortDateModel extends TimePeriodModel {
    public ShortDateModel(String value, String order) {
        setDateModel(value, order);
    }

    // TODO: "instituit in decembrie 1915 - desfiintat in 1973"
    // TODO: "09 1875"
    private void setDateModel(String value, String order) {
        String[] intervalValues = value.split(ShortDateRegex.REGEX_DATE_INTERVAL_SEPARATOR);

        if (intervalValues.length == 2) {
            setEra(intervalValues[0], intervalValues[1]);

            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);
            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);

            setDate(endValue, order, TimeUtils.END_PLACEHOLDER);
            setDate(startValue, order, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            String preparedValue = TimeUtils.clearChristumNotation(value);

            setDate(preparedValue, order, TimeUtils.END_PLACEHOLDER);
            setDate(preparedValue, order, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setDate(String value, String order, String position) {
        String preparedValue = Date.prepareDate(value);
        String[] values = preparedValue.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.MY_PLACEHOLDER)) {
            // For cases similar with "septembrie - octombrie 1919", we need to extract
            // the startYear from the section that stores the endYear
            String year = position.equals(TimeUtils.START_PLACEHOLDER) && values.length == 1
                    ? String.valueOf(this.yearEnd)
                    : values[1];
            String month = Date.getMonthName(values[0].trim());

            setMillennium(year, position);
            setCentury(year, position);
            setYear(year, position);
            setMonth(month, position);
        }
    }
}
