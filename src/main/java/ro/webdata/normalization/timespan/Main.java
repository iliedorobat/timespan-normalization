package ro.webdata.normalization.timespan;

import ro.webdata.echo.commons.File;
import ro.webdata.normalization.timespan.commons.ParamsUtils;
import ro.webdata.normalization.timespan.ro.analysis.TimespanAnalysis;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String LIDO_DATASET_PATH = File.PATH_DATASET_DIR + File.FILE_SEPARATOR + "lido/";
    private static final String PATH_OUTPUT_ADDITIONAL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_additional" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_all" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_OUTPUT_UNIQUE_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR +
            "timespan_unique" + File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);
        boolean python = ParamsUtils.python(list);

        if (python) {
            int port = ParamsUtils.gatewayPort(list);
            final Gateway gateway = new Gateway(port);
            gateway.startServer();
        }

        if (ParamsUtils.contains(list, "--expression")) {
            Demo.main(args);
        } else if (ParamsUtils.contains(list, "--analysis")) {
            boolean historicalOnly = ParamsUtils.historicalOnly(list);
            boolean sanitize = ParamsUtils.sanitize(list);

            // Extract time expressions from LIDO datasets and write them to files for validation
            TimespanAnalysis.write(LIDO_DATASET_PATH, PATH_OUTPUT_ALL_TIMESPAN_FILE, true, false, historicalOnly, sanitize);
            TimespanAnalysis.write(LIDO_DATASET_PATH, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, true, true, historicalOnly, sanitize);

//            TimespanAnalysis.writeDetails(LIDO_DATASET_PATH, PATH_OUTPUT_ALL_TIMESPAN_FILE, true, false, historicalOnly, sanitize);
//            TimespanAnalysis.writeDetails(LIDO_DATASET_PATH, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, true, true, historicalOnly, sanitize);

            // Write additional time expressions to files for validation
            TimespanAnalysis.writeAdditionalData(PATH_OUTPUT_ADDITIONAL_TIMESPAN_FILE, historicalOnly, sanitize);
        }
    }
}
