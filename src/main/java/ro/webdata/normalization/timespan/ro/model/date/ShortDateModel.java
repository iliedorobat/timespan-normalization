package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.date.ShortDateRegex;

/**
 * Used for date presented as month-year format<br/>
 * E.g.:<br/>
 *      * MY: "octombrie 1639"; "ianuarie 632"; "septembrie - octombrie 1919"; "09 1875"
 */
public class ShortDateModel extends TimePeriodModel {
    public ShortDateModel(String original, String value, String order, boolean historicalOnly) {
        setDateModel(original, value, order, historicalOnly);
    }

    private void setDateModel(String original, String value, String order, boolean historicalOnly) {
        String[] intervalValues = value.split(ShortDateRegex.REGEX_DATE_INTERVAL_SEPARATOR);

        if (intervalValues.length == 2) {
            setEra(original, intervalValues[0], intervalValues[1], true);

            String endMonth = getMonth(intervalValues[0]);
            String endYear = getYear(intervalValues[0], intervalValues[1], order, TimeUtils.END_PLACEHOLDER);
            setDate(original, endYear, endMonth, order, TimeUtils.END_PLACEHOLDER, historicalOnly);

            String startMonth = getMonth(intervalValues[1]);
            String startYear = getYear(intervalValues[0], intervalValues[1], order, TimeUtils.START_PLACEHOLDER);
            setDate(original, startYear, startMonth, order, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(original, value, value, true);

            String endMonth = getMonth(value);
            String endYear = getYear(value, value, order, TimeUtils.END_PLACEHOLDER);
            setDate(original, endYear, endMonth, order, TimeUtils.END_PLACEHOLDER, historicalOnly);

            String startMonth = getMonth(value);
            String startYear = getYear(value, value, order, TimeUtils.START_PLACEHOLDER);
            setDate(original, startYear, startMonth, order, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private void setDate(String original, String year, String month, String order, String position, boolean historicalOnly) {
        if (order.equals(TimeUtils.MY_PLACEHOLDER)) {
            setMillennium(original, year, position, historicalOnly);
            setCentury(original, year, position, historicalOnly);
            setYear(original, year, position, historicalOnly);
            setMonth(original, month, position, historicalOnly);
        }
    }

    private String getYear(String startDate, String endDate, String order, String position) {
        String[] startValues = splitDate(startDate);
        String[] endValues = splitDate(endDate);

        if (order.equals(TimeUtils.MY_PLACEHOLDER)) {
            // For cases like "septembrie - octombrie 1919", we need to extract
            // the startYear from the section that stores the endYear
            if (position.equals(TimeUtils.START_PLACEHOLDER)) {
                return startValues.length > 1
                        ? startValues[1]
                        : endValues[1];
            } else if (position.equals(TimeUtils.END_PLACEHOLDER)) {
                return endValues[1];
            }
        }

        return null;
    }

    private String getMonth(String date) {
        String preparedValue = Date.prepareDate(date);
        String[] values = preparedValue.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        return Date.getMonthName(values[0].trim());
    }

    private String[] splitDate(String date) {
        String preparedValue = TimeUtils.clearChristumNotation(date);
        preparedValue = Date.prepareDate(preparedValue);

        return preparedValue.split(TimespanRegex.REGEX_DATE_SEPARATOR);
    }
}
