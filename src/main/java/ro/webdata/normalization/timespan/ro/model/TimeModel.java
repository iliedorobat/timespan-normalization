package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Date;
import ro.webdata.echo.commons.error.TooBigCenturyException;
import ro.webdata.echo.commons.error.TooBigMillenniumException;
import ro.webdata.echo.commons.error.TooBigYearException;
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

    protected void setEra(String original, String startValue, String endValue, boolean isDate) {
        boolean hasStartEra = hasChristumNotation(startValue);
        boolean hasEndEra = hasChristumNotation(endValue);

        if (hasStartEra && hasEndEra) {
            this.eraStart = TimeUtils.getEraName(startValue);
            this.eraEnd = TimeUtils.getEraName(endValue);
        }
        else if (!hasStartEra && !hasEndEra) {
            Integer start = TimePeriodUtils.timePeriodToNumber(startValue, isDate);
            Integer end = TimePeriodUtils.timePeriodToNumber(endValue, isDate);

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

    protected void setMillennium(String original, String yearStr, String position, boolean historicalOnly) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (historicalOnly) {
                    throw new TooBigMillenniumException("setting millennium from \"" + original + "\"", position, year);
                }
                if (EnvConst.PRINT_ERROR) {
                    TooBigMillenniumException.printMessage("setting millennium from \"" + original + "\"", position, year);
                }
            }

            int millennium = TimeUtils.yearToMillennium(year);
            setMillennium(original, millennium, position, historicalOnly);
        } catch (NumberFormatException | TooBigMillenniumException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void setMillennium(String original, Integer millennium, String position, boolean historicalOnly) {
        try {
            if (millennium != null) {
                if (millennium > Date.LAST_UPDATE_MILLENNIUM && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                    if (historicalOnly) {
                        this.millenniumStart = null;
                        this.millenniumEnd = null;

                        throw new TooBigMillenniumException("setting millennium from \"" + original + "\"", position, millennium);
                    }
                    if (EnvConst.PRINT_ERROR) {
                        TooBigMillenniumException.printMessage("setting millennium from \"" + original + "\"", position, millennium);
                    }
                }

                if (position != null) {
                    if (position.equals(TimeUtils.START_PLACEHOLDER))
                        this.millenniumStart = millennium;
                    else if (position.equals(TimeUtils.END_PLACEHOLDER))
                        this.millenniumEnd = millennium;
                }
            }
        } catch (TooBigMillenniumException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void setCentury(String original, String yearStr, String position, boolean historicalOnly) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (historicalOnly) {
                    throw new TooBigYearException("setting century from \"" + original + "\"", position, year);
                }
                if (EnvConst.PRINT_ERROR) {
                    TooBigYearException.printMessage("setting century from \"" + original + "\"", position, year);
                }
            }

            /*
             * E.g.: the year 100 is part of the first century
             * Math.floor(100 / 100) + 0 = 1st century
             * Math.floor(101 / 100) + 1 = 2nd century
             */
            int buffer = year % 100 == 0 ? 0 : 1;
            int century = (int) (Math.floor(year / 100) + buffer);
            setCentury(original, century, position, historicalOnly);
        } catch (NumberFormatException | TooBigYearException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void setCentury(String original, Integer century, String position, boolean historicalOnly) {
        try {
            if (century != null) {
                if (century > Date.LAST_UPDATE_CENTURY && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                    if (historicalOnly) {
                        this.centuryStart = null;
                        this.centuryEnd = null;

                        throw new TooBigCenturyException("setting century from \"" + original + "\"", position, century);
                    }
                    if (EnvConst.PRINT_ERROR) {
                        TooBigCenturyException.printMessage("setting century from \"" + original + "\"", position, century);
                    }
                }

                if (position != null) {
                    int millennium = TimeUtils.centuryToMillennium(century);
                    setMillennium(original, millennium, position, historicalOnly);

                    if (position.equals(TimeUtils.START_PLACEHOLDER))
                        this.centuryStart = century;
                    else if (position.equals(TimeUtils.END_PLACEHOLDER))
                        this.centuryEnd = century;
                }
            }
        } catch (TooBigCenturyException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void setYear(String original, String yearStr, String position, boolean historicalOnly) {
        try {
            int year = Integer.parseInt(TimeUtils.clearDate(yearStr));
            if (year > Date.LAST_UPDATE_YEAR && eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
                if (historicalOnly) {
                    throw new TooBigYearException("setting year from \"" + original + "\"", position, year);
                }
                if (EnvConst.PRINT_ERROR) {
                    TooBigYearException.printMessage("setting year from \"" + original + "\"", position, year);
                }
            }

            if (position.equals(TimeUtils.START_PLACEHOLDER))
                this.yearStart = year;
            else if (position.equals(TimeUtils.END_PLACEHOLDER))
                this.yearEnd = year;
        } catch (NumberFormatException | TooBigYearException e) {
            System.err.println(e.getMessage());
        }
    }

    protected void setMonth(String original, String month, String position, boolean historicalOnly) {
        if (position.equals(TimeUtils.START_PLACEHOLDER))
            this.monthStart = month;
        else if (position.equals(TimeUtils.END_PLACEHOLDER))
            this.monthEnd = month;
    }

    protected void setDay(String original, String dayStr, String position, boolean historicalOnly) {
        try {
            int day = Integer.parseInt(dayStr);

            if (position.equals(TimeUtils.START_PLACEHOLDER))
                this.dayStart = day;
            else if (position.equals(TimeUtils.END_PLACEHOLDER))
                this.dayEnd = day;
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
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
