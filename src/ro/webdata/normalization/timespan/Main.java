package ro.webdata.normalization.timespan;

import ro.webdata.normalization.timespan.ro.LidoXmlTimespanAnalysis;
import ro.webdata.echo.commons.File;

public class Main {
    private static final String LIDO_DATASET_PATH = File.PATH_DATASET_DIR + File.FILE_SEPARATOR + "lido/";
    private static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_all" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_OUTPUT_UNIQUE_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_unique" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;

    public static void main(String[] args) {
        // Extract time expressions from LIDO datasets
        LidoXmlTimespanAnalysis.writeAll(LIDO_DATASET_PATH, PATH_OUTPUT_ALL_TIMESPAN_FILE, null);
        LidoXmlTimespanAnalysis.writeUnique(LIDO_DATASET_PATH, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, null);
    }
}
