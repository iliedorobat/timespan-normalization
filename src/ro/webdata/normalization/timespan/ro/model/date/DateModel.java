package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;

/**
 * Used for date presented as day-month-year or year-month-day format.<br/>
 * E.g.:<br/>
 *      * DMY: "14 ianuarie 1497", "21/01/1916", "01.11.1668", "1.09.1607", "17/29 octombrie 1893"<br/>
 *      * YMD: "1974-05-05", "1891 decembrie 07", "1738, MAI, 4"
 */
public class DateModel extends TimePeriodModel {
    public DateModel(String value, String order) {
        setDateModel(value, order);
    }

    private void setDateModel(String value, String order) {
        String[] intervalValues = value.split(DateRegex.REGEX_DATE_INTERVAL_SEPARATOR);

        if (intervalValues.length == 2) {
            setEra(
                    getYear(intervalValues[0], order),
                    getYear(intervalValues[1], order)
            );
            setDate(intervalValues[0], intervalValues[1], order);
        } else {
            setEra(
                    getYear(value, order),
                    getYear(value, order)
            );
            setDate(value, value, order);
        }
    }

    private void setDate(String startDate, String endDate, String order) {
        setDateTime(endDate, order, TimeUtils.END_PLACEHOLDER);
        setDateTime(startDate, order, TimeUtils.START_PLACEHOLDER);
    }

    private void setDateTime(String date, String order, String position) {
        String preparedDate = TimeUtils.clearChristumNotation(date);
        String year = getYear(preparedDate, order);
        String month = getMonth(preparedDate, order);
        String day = getDay(preparedDate, order);

        setMillennium(year, position);
        setCentury(year, position);
        setYear(year, position);
        setMonth(month, position);
        setDay(day, position);
    }

    private static String getYear(String date, String order) {
        String preparedDate = Date.prepareDate(date);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        // values.length == 4 if the month name is abbreviated (E.g.: "aug.")
        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            return values.length == 4 ? values[3] : values[2];
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            return values[0];
        }

        return null;
    }

    private static String getMonth(String date, String order) {
        String preparedDate = Date.prepareDate(date);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            return Date.getMonthName(values[1].trim());
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            return Date.getMonthName(values[1].trim());
        }

        return null;
    }

    private static String getDay(String date, String order) {
        String preparedDate = Date.prepareDate(date);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            return values[0];
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            return values.length == 4 ? values[3] : values[2];
        }

        return null;
    }
}
