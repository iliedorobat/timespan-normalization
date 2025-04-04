package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Collection;
import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.DBpediaTimeUtils;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.TimespanType;

import java.util.TreeSet;

public class TimePeriodModel extends TimeModel {
    public TimePeriodModel() {}

    @Override
    public String toString() {
        TreeSet<String> timePeriodSet = new TreeSet<>();
        TreeSet<String> millenniumSet = getMillenniumSet(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, true);
        TreeSet<String> centurySet = getCenturySet(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, true);
        TreeSet<String> yearSet = getYearSet(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, false);

        timePeriodSet.addAll(millenniumSet);
        timePeriodSet.addAll(centurySet);
        timePeriodSet.addAll(yearSet);

        return Collection.treeSetToDbpediaString(timePeriodSet);
    }

    public String toDBpediaStartUri(String timespanType) {
        if (timespanType == null) {
            return null;
        }

        switch (timespanType) {
            case TimespanType.CENTURY:
                return DBpediaTimeUtils.prepareUri(eraStart, centuryStart, timespanType);
            case TimespanType.MILLENNIUM:
                return DBpediaTimeUtils.prepareUri(eraStart, millenniumStart, timespanType);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return DBpediaTimeUtils.prepareUri(eraStart, yearStart, timespanType);
            default:
                return null;
        }
    }

    public String toDBpediaEndUri(String timespanType) {
        if (timespanType == null) {
            return null;
        }

        switch (timespanType) {
            case TimespanType.CENTURY:
                return DBpediaTimeUtils.prepareUri(eraEnd, centuryEnd, timespanType);
            case TimespanType.MILLENNIUM:
                return DBpediaTimeUtils.prepareUri(eraEnd, millenniumEnd, timespanType);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return DBpediaTimeUtils.prepareUri(eraEnd, yearEnd, timespanType);
            default:
                return null;
        }
    }

    public static TreeSet<String> getMillenniumSet(String eraStart, String eraEnd, Integer millenniumStart, Integer millenniumEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, millenniumStart, millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, ordinal);
    }

    public static TreeSet<String> getCenturySet(String eraStart, String eraEnd, Integer centuryStart, Integer centuryEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, centuryStart, centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, ordinal);
    }

    public static TreeSet<String> getYearSet(String eraStart, String eraEnd, Integer yearStart, Integer yearEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, yearStart, yearEnd, "", ordinal);
    }

    public static TreeSet<String> getTimeperiodSet(String eraStart, String eraEnd, Integer start, Integer end, String timePlaceholder, boolean ordinal) {
        TreeSet<String> set = new TreeSet<>();

        if (start != null && end != null) {
            pushSameBc(eraStart, eraEnd, start, end, timePlaceholder, set, ordinal);
            pushSameAd(eraStart, eraEnd, start, end, timePlaceholder, set, ordinal);
            pushBcAd(eraStart, eraEnd, start, end, timePlaceholder, set, ordinal);
            pushAdBc(eraStart, eraEnd, start, end, timePlaceholder, set, ordinal);
        }

        return set;
    }

    /**
     * Push a time period where the starting era and the ending era are both
     * TimeUtils.CHRISTUM_AD_PLACEHOLDER<br/>
     * E.g.:<br/>
     *      * sec. VI p.Chr - sec. II-lea p.Chr<br/>
     *      * sec. II p.Chr - sec. VI-lea p.Chr
     * @param eraStart The starting era
     * @param eraEnd The ending era
     * @param timeStart The starting time period
     * @param timeEnd The ending time period
     * @param timePlaceholder Placeholder for a time period:<br/>
     *                        * Const.CENTURY_PLACEHOLDER<br/>
     *                        * Const.MILLENNIUM_PLACEHOLDER<br/>
     *                        * Const.EMPTY_VALUE_PLACEHOLDER
     * @param timeSet The set where DBPedia time periods will be stored
     * @param ordinal Tell the user whether "TimeUtils.getOrdinal" should be used or not
     *                E.g: "1990" should be used as it is
     *                E.g.: "century ix" should become "9th century"
     */
    private static void pushSameAd(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet,
            boolean ordinal
    ) {
        if (eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            int start = Math.min(timeStart, timeEnd);
            int end = Math.max(timeStart, timeEnd);

            for (int timePeriod = start; timePeriod <= end; timePeriod++) {
                String period = ordinal ? TimeUtils.getOrdinal(timePeriod) : String.valueOf(timePeriod);
                String timeDbpedia = period
                        + timePlaceholder;
                timeSet.add(timeDbpedia);
            }
        }
    }

    /**
     * Push a time period where the starting era and the ending era are both
     * TimeUtils.CHRISTUM_BC_PLACEHOLDER<br/>
     * E.g.:<br/>
     *      * sec. VI a.Chr - sec. II-lea a.Chr<br/>
     *      * sec. II a.Chr - sec. VI-lea a.Chr
     * @param eraStart The starting era
     * @param eraEnd The ending era
     * @param timeStart The starting time period
     * @param timeEnd The ending time period
     * @param timePlaceholder Placeholder for a time period:<br/>
     *                        * Const.CENTURY_PLACEHOLDER<br/>
     *                        * Const.MILLENNIUM_PLACEHOLDER<br/>
     *                        * Const.EMPTY_VALUE_PLACEHOLDER
     * @param timeSet The set where DBPedia time periods will be stored
     * @param ordinal Tell the user whether "TimeUtils.getOrdinal" should be used or not
     *                E.g.: "century ix" should become "9th century"
     *                E.g: "1990" should be used as it is
     */
    private static void pushSameBc(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet,
            boolean ordinal
    ) {
        if (eraStart.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)) {
            int start = Math.max(timeStart, timeEnd);
            int end = Math.min(timeStart, timeEnd);

            for (int timePeriod = start; timePeriod >= end; timePeriod--) {
                String period = ordinal ? TimeUtils.getOrdinal(timePeriod) : String.valueOf(timePeriod);
                String timeDbpedia = period
                        + timePlaceholder
                        + Const.UNDERSCORE_PLACEHOLDER
                        + TimeUtils.CHRISTUM_BC_LABEL;
                timeSet.add(timeDbpedia);
            }
        }
    }

    private static void pushBcAd(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet,
            boolean ordinal
    ) {
        // sec. VI a.Chr - sec. II-lea p.Chr
        if (eraStart.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)) {
            int start = timeStart;
            int end = 1;
            pushSameBc(
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet,
                    ordinal
            );

            start = 1;
            end = timeEnd;
            pushSameAd(
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet,
                    ordinal
            );
        }
    }

    private static void pushAdBc(
            String eraStart,
            String eraEnd,
            Integer timeStart,
            Integer timeEnd,
            String timePlaceholder,
            TreeSet<String> timeSet,
            boolean ordinal
    ) {
        // sec. II p.Chr - sec. VI-lea a.Chr
        if (eraStart.equals(TimeUtils.CHRISTUM_AD_PLACEHOLDER)
                && eraEnd.equals(TimeUtils.CHRISTUM_BC_PLACEHOLDER)) {
            int start = timeEnd;
            int end = 1;
            pushSameBc(
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    TimeUtils.CHRISTUM_BC_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet,
                    ordinal
            );

            start = 1;
            end = timeStart;
            pushSameAd(
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    TimeUtils.CHRISTUM_AD_PLACEHOLDER,
                    start,
                    end,
                    timePlaceholder,
                    timeSet,
                    ordinal
            );
        }
    }
}
