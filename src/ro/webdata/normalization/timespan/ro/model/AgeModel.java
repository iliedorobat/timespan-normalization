package ro.webdata.normalization.timespan.ro.model;

import ro.webdata.normalization.timespan.ro.regex.AgeRegex;

public class AgeModel extends TimePeriodModel {
    private String age;

    public AgeModel(TimespanModel timespanModel, String original, String age, String regex) {
        this.age = mapAge(age, regex);

        setMillennium(timespanModel, original, (Integer) null, null);
        setCentury(timespanModel, original, (Integer) null, null);
    }

    private String mapAge(String age, String regex) {
        switch (regex) {
            case AgeRegex.AURIGNACIAN_CULTURE:
                return "Aurignacian";
            case AgeRegex.BRONZE_AGE:
                return "Bronze_Age";
            case AgeRegex.CHALCOLITHIC_AGE:
                return "Chalcolithic";
            case AgeRegex.FRENCH_CONSULATE_AGE:
                return "French_Consulate";
            case AgeRegex.HALLSTATT_CULTURE:
                return "Hallstatt_culture";
            case AgeRegex.INTERWAR_PERIOD:
                return "Interwar_period";
            case AgeRegex.MIDDLE_AGES:
                return "Middle_Ages";
            case AgeRegex.MESOLITHIC_AGE:
                return "Mesolithic";
            case AgeRegex.MODERN_AGES:
                return "Modern_history";
            case AgeRegex.NEOLITHIC_AGE:
                return "Neolithic";
            case AgeRegex.NERVA_ANTONINE_DYNASTY:
                return "Nerva–Antonine_dynasty";
            case AgeRegex.PLEISTOCENE_AGE:
                return "Pleistocene";
            case AgeRegex.PTOLEMAIC_DYNASTY:
                return "Ptolemaic_dynasty";
            case AgeRegex.RENAISSANCE:
                return "Renaissance";
            case AgeRegex.ROMAN_EMPIRE_AGE:
                return "Roman_Empire";
            case AgeRegex.WW_I_PERIOD:
                return "World_War_I";
            case AgeRegex.WW_II_PERIOD:
                return "World_War_II";
            default:
                return age;
        }
    }
}
