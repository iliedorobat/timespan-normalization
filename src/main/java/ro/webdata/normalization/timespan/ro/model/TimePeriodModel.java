package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.echo.commons.Collection;
import ro.webdata.echo.commons.Const;
import ro.webdata.normalization.timespan.ro.TimeUtils;
import ro.webdata.normalization.timespan.ro.TimespanType;

import java.util.LinkedHashSet;
import java.util.Set;

public class TimePeriodModel extends TimeModel {
    public TimePeriodModel() {}

    @Override
    public String toString() {
        Set<String> timePeriodSet = new LinkedHashSet<>();
        Set<String> millenniumSet = getMillenniumSet(this.eraStart, this.eraEnd, this.millenniumStart, this.millenniumEnd, true);
        Set<String> centurySet = getCenturySet(this.eraStart, this.eraEnd, this.centuryStart, this.centuryEnd, true);
        Set<String> yearSet = getYearSet(this.eraStart, this.eraEnd, this.yearStart, this.yearEnd, false);

        timePeriodSet.addAll(millenniumSet);
        timePeriodSet.addAll(centurySet);
        timePeriodSet.addAll(yearSet);

        return Collection.treeSetToDbpediaString(timePeriodSet);
    }

    public String toDBpediaStartUri(String matchedType) {
        if (matchedType == null) {
            return null;
        }

        switch (matchedType) {
            case TimespanType.CENTURY:
                return DBpediaModel.prepareUri(eraStart, centuryStart, matchedType);
            case TimespanType.MILLENNIUM:
                return DBpediaModel.prepareUri(eraStart, millenniumStart, matchedType);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return DBpediaModel.prepareUri(eraStart, yearStart, matchedType);
            default:
                return null;
        }
    }

    public String toDBpediaEndUri(String matchedType) {
        if (matchedType == null) {
            return null;
        }

        switch (matchedType) {
            case TimespanType.CENTURY:
                return DBpediaModel.prepareUri(eraEnd, centuryEnd, matchedType);
            case TimespanType.MILLENNIUM:
                return DBpediaModel.prepareUri(eraEnd, millenniumEnd, matchedType);
            case TimespanType.DATE:
            case TimespanType.YEAR:
                return DBpediaModel.prepareUri(eraEnd, yearEnd, matchedType);
            default:
                return null;
        }
    }

    public static Set<String> getMillenniumSet(String eraStart, String eraEnd, Integer millenniumStart, Integer millenniumEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, millenniumStart, millenniumEnd, Const.DBPEDIA_MILLENNIUM_PLACEHOLDER, ordinal);
    }

    public static Set<String> getCenturySet(String eraStart, String eraEnd, Integer centuryStart, Integer centuryEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, centuryStart, centuryEnd, Const.DBPEDIA_CENTURY_PLACEHOLDER, ordinal);
    }

    public static Set<String> getYearSet(String eraStart, String eraEnd, Integer yearStart, Integer yearEnd, boolean ordinal) {
        return getTimeperiodSet(eraStart, eraEnd, yearStart, yearEnd, "", ordinal);
    }

    public static Set<String> getTimeperiodSet(String eraStart, String eraEnd, Integer start, Integer end, String timePlaceholder, boolean ordinal) {
        Set<String> set = new LinkedHashSet<>();

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
            Set<String> timeSet,
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
            Set<String> timeSet,
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
            Set<String> timeSet,
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
            Set<String> timeSet,
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
