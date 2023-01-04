package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Date;
import ro.webdata.echo.commons.Print;
import ro.webdata.normalization.timespan.commons.EnvConst;
import ro.webdata.normalization.timespan.ro.TimePeriodUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;

public class TimeModel {
    protected String eraStart, eraEnd;
    protected Integer millenniumStart, millenniumEnd;
    protected Integer centuryStart, centuryEnd;
    protected Integer yearStart, yearEnd;
    protected String monthStart, monthEnd;
    protected int dayStart, dayEnd;
    protected boolean isInterval = false;

    protected void setEra(String startValue, String endValue) {
        boolean hasStartEra = hasChristumNotation(startValue);
        boolean hasEndEra = hasChristumNotation(endValue);

        if (hasStartEra && hasEndEra) {
            this.eraStart = TimeUtils.getEraName(startValue);
            this.eraEnd = TimeUtils.getEraName(endValue);
        }
        else if (!hasStartEra && !hasEndEra) {
            Integer start = TimePeriodUtils.timePeriodToNumber(startValue);
            Integer end = TimePeriodUtils.timePeriodToNumber(endValue);

            if (start == null || end == null) {
                this.eraStart = TimeUtils.CHRISTUM_AD_PLACEHOLDER;
                this.eraEnd = TimeUtils.CHRISTUM_AD_PLACEHOLDER;
            } else if (start > end) {
                this.eraStart = TimeUtils.CHRISTUM_BC_PLACEHOLDER;
                this.eraEnd = TimeUtils.CHRISTUM_BC_PLACEHOLDER;
            } else {
                this.eraStart = TimeUtils.CHRISTUM_AD_PLACEHOLDER;
                this.eraEnd = TimeUtils.CHRISTUM_AD_PLACEHOLDER;
            }
        }
        // E.g.: "sec. i a.chr. - sec. i"
        else if (hasStartEra && !hasEndEra) {
            this.eraStart = TimeUtils.getEraName(startValue);
            this.eraEnd = TimeUtils.CHRISTUM_AD_PLACEHOLDER;
        }
        else if (!hasStartEra && hasEndEra) {
            String endEra = TimeUtils.getEraName(endValue);
            this.eraStart = endEra;
            this.eraEnd = endEra;
        }
    }

    protected void setMillennium(String yearStr, String position) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (EnvConst.PRINT_ERROR) {
                    Print.tooBigYear("setting millennium", position, year);
                }
            } else {
                int millennium = TimeUtils.yearToMillennium(year);
                setMillennium(millennium, position);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    protected void setMillennium(Integer millennium, String position) {
        Integer millenniumStart = millennium;
        Integer millenniumEnd = millennium;

        if (millennium != null && millennium > Date.LAST_UPDATE_MILLENNIUM && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            if (EnvConst.PRINT_ERROR) {
                Print.tooBigMillennium("setting millennium", position, millennium);
            }
            millenniumStart = null;
            millenniumEnd = null;
        }

        if (position != null) {
            if (position.equals(TimeUtils.START_PLACEHOLDER))
                this.millenniumStart = millenniumStart;
            else if (position.equals(TimeUtils.END_PLACEHOLDER))
                this.millenniumEnd = millenniumEnd;
        }
    }

    protected void setCentury(String yearStr, String position) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (EnvConst.PRINT_ERROR) {
                    Print.tooBigYear("setting century", position, year);
                }
            } else {
                /**
                 * E.g.: the year 100 is part of the first century
                 * Math.floor(100 / 100) + 0 = 1st century
                 * Math.floor(101 / 100) + 1 = 2nd century
                 */
                int buffer = year % 100 == 0 ? 0 : 1;
                int century = (int) (Math.floor(year / 100) + buffer);
                setCentury(century, position);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    protected void setCentury(Integer century, String position) {
        Integer centuryStart = century;
        Integer centuryEnd = century;

        if (century != null && century > Date.LAST_UPDATE_CENTURY && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            if (EnvConst.PRINT_ERROR) {
                Print.tooBigCentury("setting century", position, century);
            }
            centuryStart = null;
            centuryEnd = null;
        }

        if (position != null) {
            if (position.equals(TimeUtils.START_PLACEHOLDER))
                this.centuryStart = centuryStart;
            else if (position.equals(TimeUtils.END_PLACEHOLDER))
                this.centuryEnd = centuryEnd;
        }
    }

    protected void setYear(String yearStr, String position) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (EnvConst.PRINT_ERROR) {
                    Print.tooBigYear("setting year", position, year);
                }
            } else {
                if (position.equals(TimeUtils.START_PLACEHOLDER))
                    this.yearStart = year;
                else if (position.equals(TimeUtils.END_PLACEHOLDER))
                    this.yearEnd = year;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    protected void setMonth(String month, String position) {
        if (position.equals(TimeUtils.START_PLACEHOLDER))
            this.monthStart = month;
        else if (position.equals(TimeUtils.END_PLACEHOLDER))
            this.monthEnd = month;
    }

    protected void setDay(String dayStr, String position) {
        try {
            int day = Integer.parseInt(dayStr);

            if (position.equals(TimeUtils.START_PLACEHOLDER))
                this.dayStart = day;
            else if (position.equals(TimeUtils.END_PLACEHOLDER))
                this.dayEnd = day;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the input value contain formatted Christum notation
     * ("__AD__" or "__BC__")
     * @param value The input value
     * @return True/False
     */
    private boolean hasChristumNotation(String value) {
        return value.contains(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                || value.contains(TimeUtils.CHRISTUM_AD_PLACEHOLDER);
    }
}
