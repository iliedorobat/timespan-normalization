package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;

/**
 * Used for date presented as day-month-year or year-month-day format.<br/>
 * E.g.:<br/>
 *      * DMY: "14 ianuarie 1497", "21/01/1916", "01.11.1668", "1.09.1607"<br/>
 *      * YMD: "1974-05-05", "1891 decembrie 07", "1738, MAI, 4"
 */
public class DateModel extends TimePeriodModel {
    public DateModel(String value, String order) {
        setDateModel(value, order);
    }

    //TODO: "17/29 octombrie 1893"
    private void setDateModel(String value, String order) {
        String[] intervalValues = value.split(DateRegex.REGEX_DATE_INTERVAL_SEPARATOR);

        if (intervalValues.length == 2) {
            setEra(intervalValues[0], intervalValues[1]);

            String endValue = TimeUtils.clearChristumNotation(intervalValues[1]);
            String startValue = TimeUtils.clearChristumNotation(intervalValues[0]);

            setDate(endValue, order, TimeUtils.END_PLACEHOLDER);
            setDate(startValue, order, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(value, value);

            String preparedValue = TimeUtils.clearChristumNotation(value);

            setDate(preparedValue, order, TimeUtils.END_PLACEHOLDER);
            setDate(preparedValue, order, TimeUtils.START_PLACEHOLDER);
        }
    }

    // values.length == 4 if the month name is abbreviated (E.g.: "aug.")
    private void setDate(String value, String order, String position) {
        String year = null, month = null, day = null;
        String preparedValue = Date.prepareDate(value);
        String[] values = preparedValue.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            year = values.length == 4 ? values[3] : values[2];
            month = Date.getMonthName(values[1].trim());
            day = values[0];
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            year = values[0];
            month = Date.getMonthName(values[1].trim());
            day = values.length == 4 ? values[3] : values[2];
        }

        setMillennium(year, position);
        setCentury(year, position);
        setYear(year, position);
        setMonth(month, position);
        setDay(day, position);
    }
}
