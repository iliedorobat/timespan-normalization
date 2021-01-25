package test.java.ro.webdata.normalization.timespan.ro.commons;

import ro.webdata.echo.commons.File;

public class Const {
    public static final String CSV_SEPARATOR = "|";
    public static final String DASH_PLACEHOLDER = "-";

    private static final String FILE_NAME_ARHEOLOGIE = "inp-clasate-arheologie-2014-02-02";
    private static final String FILE_NAME_ARTA = "inp-clasate-arta-2014-02-02";
    private static final String FILE_NAME_ARTE_DECO = "inp-clasate-arte-decorative-2014-02-02";
    private static final String FILE_NAME_DOC = "inp-clasate-documente-2014-02-02";
    private static final String FILE_NAME_ETNO = "inp-clasate-etnografie-2014-02-02";
    private static final String FILE_NAME_ST_TEH = "inp-clasate-istoria-stiintei-si-tehnicii-2014-02-02";
    private static final String FILE_NAME_ISTORIE = "inp-clasate-istorie-2014-02-02";
    private static final String FILE_NAME_MEDALISTICA = "inp-clasate-medalistica-2014-02-02";
    private static final String FILE_NAME_NUMISMATICA = "inp-clasate-numismatica-2014-02-02";
    private static final String FILE_NAME_ST_NAT = "inp-clasate-stiintele-naturii-2014-02-03-7";

    public static String[] fileNames = {
            FILE_NAME_ARHEOLOGIE,
            FILE_NAME_ARTA,
            FILE_NAME_ARTE_DECO,
            FILE_NAME_DOC,
            FILE_NAME_ETNO,
            FILE_NAME_ST_TEH,
            FILE_NAME_ISTORIE,
            FILE_NAME_MEDALISTICA,
            FILE_NAME_NUMISMATICA,
            FILE_NAME_ST_NAT
    };

    public static final String PATH_INPUT_LIDO_FILES = File.USER_HOME + File.FILE_SEPARATOR + "workspace" + File.FILE_SEPARATOR +
            "personal" + File.FILE_SEPARATOR + "enriching-cultural-heritage-metadata" + File.FILE_SEPARATOR +
            "files" + File.FILE_SEPARATOR + "input" + File.FILE_SEPARATOR + "lido" + File.FILE_SEPARATOR;
    public static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = File.PATH_DATA_PROCESSING_DIR + File.FILE_SEPARATOR +
            "timespan_all" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    public static final String PATH_OUTPUT_UNIQUE_TIMESPAN_FILE = File.PATH_DATA_PROCESSING_DIR + File.FILE_SEPARATOR +
            "timespan_unique" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
}
