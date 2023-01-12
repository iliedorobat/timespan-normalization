package ro.webdata.normalization.timespan.ro.model.date;

import ro.webdata.echo.commons.Const;
import ro.webdata.echo.commons.Date;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.TimespanType;
import ro.webdata.normalization.timespan.ro.model.TimePeriodModel;
import ro.webdata.normalization.timespan.ro.model.TimespanModel;
import ro.webdata.normalization.timespan.ro.regex.date.LongDateRegex;

/**
 * Used for those time intervals that are stored as a "long" date
 * format (having a century, a year, a month and a day)<br/>
 * E.g.: "s:17;a:1622;l:12;z:30"
 */
public class LongDateModel extends TimePeriodModel {
    private static final String SUFFIX_CENTURY = "s:";
    private static final String SUFFIX_YEAR = "a:";
    private static final String SUFFIX_MONTH = "l:";
    private static final String SUFFIX_DAY = "z:";

    public LongDateModel(TimespanModel timespanModel, String original, String value) {
        String preparedValue = TimeUtils.clearChristumNotation(value);
        String[] values = preparedValue.split(LongDateRegex.DATE_SEPARATOR);

        setEra(value);
        for (String str : values) {
            str = str.toLowerCase();

            if (str.contains(SUFFIX_CENTURY)) {
                setCentury(timespanModel, str);
                setMillennium(timespanModel, str);
            } else if (str.contains(SUFFIX_YEAR)) {
                setYear(timespanModel, str);
            } else if (str.contains(SUFFIX_MONTH)) {
                setMonth(timespanModel, str);
            } else if (str.contains(SUFFIX_DAY)) {
                setDay(timespanModel, str);
            }
        }
        System.out.println();
    }

    private void setEra(String value) {
        String era = TimeUtils.getEraName(value);
        this.eraStart = era;
        this.eraEnd = era;
    }

    private void setMillennium(TimespanModel timespanModel, String centuryValue) {
        String centuryStr = centuryValue
                .replaceAll(SUFFIX_CENTURY, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
        try {
            int century = Integer.parseInt(centuryStr);
            int millennium = TimeUtils.centuryToMillennium(century);
            timespanModel.addNormalizedType(TimespanType.MILLENNIUM);
            this.millenniumStart = millennium;
            this.millenniumEnd = millennium;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setCentury(TimespanModel timespanModel, String value) {
        String centuryStr = value
                .replaceAll(SUFFIX_CENTURY, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
        try {
            int century = Integer.parseInt(centuryStr);
            timespanModel.addNormalizedType(TimespanType.CENTURY);
            this.centuryStart = century;
            this.centuryEnd = century;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setYear(TimespanModel timespanModel, String value) {
        String yearStr = value
                .replaceAll(SUFFIX_YEAR, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
        try {
            int year = Integer.parseInt(yearStr);
            timespanModel.addNormalizedType(TimespanType.YEAR);
            this.yearStart = year;
            this.yearEnd = year;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setMonth(TimespanModel timespanModel, String value) {
        String monthStr = value
                .replaceAll(SUFFIX_MONTH, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
        String month = Date.getMonthName(monthStr);
        this.monthStart = month;
        this.monthEnd = month;
    }

    private void setDay(TimespanModel timespanModel, String value) {
        String dayStr = value
                .replaceAll(SUFFIX_DAY, Const.EMPTY_VALUE_PLACEHOLDER)
                .trim();
        try {
            int day = Integer.parseInt(dayStr);
            this.dayStart = day;
            this.dayEnd = day;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
