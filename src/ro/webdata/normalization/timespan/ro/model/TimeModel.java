package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.normalization.timespan.commons.EnvConst;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.echo.commons.Date;
import ro.webdata.echo.commons.Print;

public class TimeModel {
    protected String eraStart, eraEnd;
    protected Integer millenniumStart, millenniumEnd;
    protected Integer centuryStart, centuryEnd;
    protected Integer yearStart, yearEnd;
    protected String monthStart, monthEnd;
    protected int dayStart, dayEnd;
    protected boolean isInterval = false;

    protected void setEra(String value, String position) {
        if (position.equals(TimeUtils.START_PLACEHOLDER)) {
            boolean containsEra = value.contains(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                    || value.contains(TimeUtils.CHRISTUM_AD_PLACEHOLDER);
            this.eraStart = !containsEra && this.eraEnd != null
                    ? TimeUtils.getEraName(this.eraEnd)
                    : TimeUtils.getEraName(value);
        } else if (position.equals(TimeUtils.END_PLACEHOLDER)) {
            this.eraEnd = TimeUtils.getEraName(value);
        }
    }

    protected void setMillennium(Integer millennium, String position) {
        Integer millenniumStart = millennium;
        Integer millenniumEnd = millennium;

        if (millennium > Date.LAST_UPDATE_MILLENNIUM && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            if (EnvConst.PRINT_ERROR) {
                Print.tooBigMillennium("setting millennium", position, millennium);
            }
            millenniumStart = null;
            millenniumEnd = null;
        }

        if (position.equals(TimeUtils.START_PLACEHOLDER))
            this.millenniumStart = millenniumStart;
        else if (position.equals(TimeUtils.END_PLACEHOLDER))
            this.millenniumEnd = millenniumEnd;
    }

    protected void setCentury(String yearStr, String position) {
        try {
            int year = Integer.parseInt(yearStr.trim());
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

        if (century > Date.LAST_UPDATE_CENTURY && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            if (EnvConst.PRINT_ERROR) {
                Print.tooBigCentury("setting century", position, century);
            }
            centuryStart = null;
            centuryEnd = null;
        }

        if (position.equals(TimeUtils.START_PLACEHOLDER))
            this.centuryStart = centuryStart;
        else if (position.equals(TimeUtils.END_PLACEHOLDER))
            this.centuryEnd = centuryEnd;
    }

    protected void setYear(String yearStr, String position) {
        try {
            int year = Integer.parseInt(yearStr.trim());

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

    protected void setIsInterval(boolean isInterval) {
        this.isInterval = isInterval;
    }
}
