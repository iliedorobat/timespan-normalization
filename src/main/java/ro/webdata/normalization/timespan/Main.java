package ro.webdata.normalization.timespan;

import ro.webdata.echo.commons.File;
import ro.webdata.normalization.timespan.commons.ParamsUtils;
import ro.webdata.normalization.timespan.ro.analysis.TimespanAnalysis;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String LIDO_DATASET_PATH = File.PATH_DATASET_DIR + File.FILE_SEPARATOR + "lido/";
    private static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_all" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_OUTPUT_UNIQUE_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_unique" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        if (ParamsUtils.contains(list, "--value")) {
            Demo.main(args);
        } else {
            // Extract time expressions from LIDO datasets
            TimespanAnalysis.write(LIDO_DATASET_PATH, PATH_OUTPUT_ALL_TIMESPAN_FILE, true, false);
            TimespanAnalysis.write(LIDO_DATASET_PATH, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, true, true);

            TimespanAnalysis.writeDetails(LIDO_DATASET_PATH, PATH_OUTPUT_ALL_TIMESPAN_FILE, true, false);
            TimespanAnalysis.writeDetails(LIDO_DATASET_PATH, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, true, true);
        }
    }
}
