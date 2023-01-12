package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;
import ro.webdata.normalization.timespan.ro.regex.date.DateRegex;

/**
 * Used for date presented as day-month-year or year-month-day format.<br/>
 * E.g.:<br/>
 *      * DMY: "14 ianuarie 1497", "21/01/1916", "01.11.1668", "1.09.1607", "17/29 octombrie 1893";
 *             "dupa 29 aprilie 1616"; "dupa 10 mai 1903"; <br/>
 *      * YMD: "1974-05-05", "1891 decembrie 07", "1738, MAI, 4"
 */
public class DateModel extends TimePeriodModel {
    public DateModel(TimespanModel timespanModel, String original, String value, String order) {
        setDateModel(timespanModel, original, value, order);
    }

    private void setDateModel(TimespanModel timespanModel, String original, String value, String order) {
        String[] intervalValues = value.split(DateRegex.REGEX_DATE_INTERVAL_SEPARATOR);

        if (intervalValues.length == 2) {
            setEra(
                    original,
                    getYear(intervalValues[0], intervalValues[1], order),
                    getYear(intervalValues[1], intervalValues[0], order),
                    true
            );
            setDate(timespanModel, original, intervalValues[0], intervalValues[1], order, TimeUtils.END_PLACEHOLDER);
            setDate(timespanModel, original, intervalValues[0], intervalValues[1], order, TimeUtils.START_PLACEHOLDER);
        } else {
            setEra(
                    original,
                    getYear(value, value, order),
                    getYear(value, value, order),
                    true
            );
            setDate(timespanModel, original, value, value, order, TimeUtils.END_PLACEHOLDER);
            setDate(timespanModel, original, value, value, order, TimeUtils.START_PLACEHOLDER);
        }
    }

    private void setDate(TimespanModel timespanModel, String original, String startDate, String endDate, String order, String position) {
        String start = TimeUtils.clearChristumNotation(startDate);
        String end = TimeUtils.clearChristumNotation(endDate);

        String mainDate = position.equals(TimeUtils.START_PLACEHOLDER) ? start : end;
        String secondDate = position.equals(TimeUtils.START_PLACEHOLDER) ? end : start;

        setDateTime(timespanModel, original, mainDate, secondDate, order, position);
    }

    private void setDateTime(TimespanModel timespanModel, String original, String firstDate, String secondDate, String order, String position) {
        String year = getYear(firstDate, secondDate, order);
        String month = getMonth(firstDate, secondDate, order);
        String day = getDay(firstDate, secondDate, order);

        setMillennium(timespanModel, original, year, position);
        setCentury(timespanModel, original, year, position);
        setYear(timespanModel, original, year, position);
        setMonth(timespanModel, original, month, position);
        setDay(timespanModel, original, day, position);
    }

    private static String getYear(String mainDate, String secondDate, String order) {
        String preparedDate = Date.prepareDate(mainDate);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        try {
            // values.length == 4 if the month name is abbreviated (E.g.: "aug.")
            if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
                return values.length == 4 ? values[3] : values[2];
            } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
                return values[0];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return getYear(secondDate, mainDate, order);
        }

        return null;
    }

    private static String getMonth(String mainDate, String secondDate, String order) {
        String preparedDate = Date.prepareDate(mainDate);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            return Date.getMonthName(values[1].trim());
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            return Date.getMonthName(values[1].trim());
        }

        return null;
    }

    private static String getDay(String mainDate, String secondDate, String order) {
        String preparedDate = Date.prepareDate(mainDate);
        String[] values = preparedDate.split(TimespanRegex.REGEX_DATE_SEPARATOR);

        if (order.equals(TimeUtils.DMY_PLACEHOLDER)) {
            return values[0];
        } else if (order.equals(TimeUtils.YMD_PLACEHOLDER)) {
            return values.length == 4 ? values[3] : values[2];
        }

        return null;
    }
}
