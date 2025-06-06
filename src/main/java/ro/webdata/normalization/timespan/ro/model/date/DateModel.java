package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.regex.TimespanRegex;

/**
 * Used for date presented as day-month-year or year-month-day format.<br/>
 * E.g.:<br/>
 *      * DMY: "14 ianuarie 1497", "21/01/1916", "01.11.1668", "1.09.1607", "17/29 octombrie 1893";
 *             "dupa 29 aprilie 1616"; "dupa 10 mai 1903"; <br/>
 *      * YMD: "1974-05-05", "1891 decembrie 07", "1738, MAI, 4"
 */
public class DateModel extends TimePeriodModel {
    public DateModel(String original, String value, String order, boolean historicalOnly) {
        setDateModel(original, value, order, historicalOnly);
    }

    private void setDateModel(String original, String value, String order, boolean historicalOnly) {
        String[] intervalValues = value.split(TimespanRegex.REGEX_INTERVAL_DELIMITER);

        if (intervalValues.length == 2) {
            setEra(
                    original,
                    getYear(intervalValues[0], intervalValues[1], order),
                    getYear(intervalValues[1], intervalValues[0], order),
                    true
            );
            setDate(original, intervalValues[0], intervalValues[1], order, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, intervalValues[0], intervalValues[1], order, TimeUtils.START_PLACEHOLDER, historicalOnly);
        } else {
            setEra(
                    original,
                    getYear(value, value, order),
                    getYear(value, value, order),
                    true
            );
            setDate(original, value, value, order, TimeUtils.END_PLACEHOLDER, historicalOnly);
            setDate(original, value, value, order, TimeUtils.START_PLACEHOLDER, historicalOnly);
        }
    }

    private void setDate(String original, String startDate, String endDate, String order, String position, boolean historicalOnly) {
        String start = TimeUtils.clearChristumNotation(startDate);
        String end = TimeUtils.clearChristumNotation(endDate);

        String mainDate = position.equals(TimeUtils.START_PLACEHOLDER) ? start : end;
        String secondDate = position.equals(TimeUtils.START_PLACEHOLDER) ? end : start;

        setDateTime(original, mainDate, secondDate, order, position, historicalOnly);
    }

    private void setDateTime(String original, String firstDate, String secondDate, String order, String position, boolean historicalOnly) {
        String year = getYear(firstDate, secondDate, order);
        String month = getMonth(firstDate, secondDate, order);
        String day = getDay(firstDate, secondDate, order);

        setMillennium(original, year, position, historicalOnly);
        setCentury(original, year, position, historicalOnly);
        setYear(original, year, position, historicalOnly);
        setMonth(original, month, position, historicalOnly);
        setDay(original, day, position, historicalOnly);
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
