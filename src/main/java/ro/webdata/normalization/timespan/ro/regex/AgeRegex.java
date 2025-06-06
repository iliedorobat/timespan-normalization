package ro.webdata.normalization.timespan.ro.regex;

import static ro.webdata.normalization.timespan.ro.regex.TimespanRegex.*;

/**
 * Regular expressions for those time intervals that point to
 * an age (epoch) time period
 */
public class AgeRegex {
    private AgeRegex() {}

    /** <a href="http://dbpedia.org/page/Pleistocene">Pleistocene</a> */
    public static final String PLEISTOCENE_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "pleistocen" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Mesolithic">Mesolithic</a> */
    public static final String MESOLITHIC_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "epipaleolitic" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Chalcolithic">Chalcolithic</a> */
    public static final String CHALCOLITHIC_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "eneolitic" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Neolithic">Neolithic</a> */
    public static final String NEOLITHIC_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "neolitic" + ANY_WORDS + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Bronze_Age">Bronze_Age</a> */
    public static final String BRONZE_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "bronz" + REGEX_OR + "bronzului" + REGEX_OR + "tarzii" + ")" + TEXT_END + ")";

    /** <a href="http://dbpedia.org/page/Aurignacian">Aurignacian</a> */
    public static final String AURIGNACIAN_CULTURE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "aurignacian" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Hallstatt_culture">Hallstatt Culture</a> */
    public static final String HALLSTATT_CULTURE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "hallstatt" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Middle_Ages">Middle Ages</a> */
    public static final String MIDDLE_AGES = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "(medieval" + REGEX_OR + "medievala)" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Modern_history">Modern History</a> */
    public static final String MODERN_AGES = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "moderna" + ")" + TEXT_END + ")";

    /** <a href="http://dbpedia.org/page/Ptolemaic_dynasty">Ptolemaic Dynasty</a> */
    public static final String PTOLEMAIC_DYNASTY = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "ptolem" + ANY_WORDS + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Roman_Empire">Roman Empire</a> */
    public static final String ROMAN_EMPIRE_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "romana" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Nerva">Nerva–Antonine Dynasty</a>–Antonine_dynasty */
    public static final String NERVA_ANTONINE_DYNASTY = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "antoninian" + ANY_WORDS + REGEX_OR + "hadrian" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Renaissance">Renaissance</a> */
    public static final String RENAISSANCE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "renastere" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/French_Consulate">French Consulate</a> */
    public static final String FRENCH_CONSULATE_AGE = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "perioada consulatului francez" + ")" + TEXT_END + ")";

    /** <a href="http://dbpedia.org/page/World_War_I">World War I</a> */
    public static final String WW_I_PERIOD = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "primul razboi mondial" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/Interwar_period">Interwar Period</a> */
    public static final String INTERWAR_PERIOD = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "interbelica" + ")" + TEXT_END + ")";
    /** <a href="http://dbpedia.org/page/World_War_II">World War II</a> */
    public static final String WW_II_PERIOD = CASE_INSENSITIVE
            + "(" + TEXT_START + "(" + "al (doilea" + REGEX_OR + "ii-lea) razboi mondial" + ")" + TEXT_END + ")";

    public static final String[] AGE_OPTIONS = {
            PLEISTOCENE_AGE,
            MESOLITHIC_AGE,
            CHALCOLITHIC_AGE,
            NEOLITHIC_AGE,
            BRONZE_AGE,
            MIDDLE_AGES,
            MODERN_AGES,
            HALLSTATT_CULTURE,
            AURIGNACIAN_CULTURE,
            PTOLEMAIC_DYNASTY,
            ROMAN_EMPIRE_AGE,
            NERVA_ANTONINE_DYNASTY,
            RENAISSANCE,
            FRENCH_CONSULATE_AGE,
            WW_I_PERIOD,
            INTERWAR_PERIOD,
            WW_II_PERIOD
    };
}
