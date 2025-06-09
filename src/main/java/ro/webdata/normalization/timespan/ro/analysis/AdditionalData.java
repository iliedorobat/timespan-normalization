package ro.webdata.normalization.timespan.ro.analysis;

import java.util.ArrayList;
import java.util.List;

public class AdditionalData {
    public static final List<String> CENTURY_TIMESPAN_LIST = new ArrayList<>() {{
        add("între 2004 şi 2008"); // S with cedilla
        add("între 2004 și 2008"); // S with comma below.
        add("INTRE 2004 SI 2008");
        add("între sec. al 2-lea a. chr. şi al 3-lea");
        add("între sec. al ii-lea a. chr. şi al iii-lea");
        add("între sec. al 2-lea a. chr. şi 3");
        add("între sec. al ii-lea a. chr. şi iii");
        add("între sec. 2 a. chr. şi 3");
        add("între sec. ii a. chr. şi iii");
        add("între sec. 2-lea a. chr. şi 3");
        add("între sec. ii-lea a. chr. şi iii");
        add("între sec. 2 a. chr. şi 1/2 sec. 3");
        add("între sec. ii a. chr. şi 1/2 sec. iii");
        add("între sec. 4 şi 1/2 sec. 2");
        add("între sec. iv şi 1/2 sec. ii");
        add("între sec. 4 şi 1/2 sec. 2 a. chr");
        add("între sec. iv şi 1/2 sec. ii a. chr");
        add("între sec. 4 şi 1/2 sec. 2 p. chr");
        add("între sec. iv şi 1/2 sec. ii p. chr");

        add("între sec. 2 î. chr. şi 1/2 sec. 3");
        add("între sec. ii î. chr. şi 1/2 sec. iii");
        add("între sec. 2 î. hr. şi 1/2 sec. 3");
        add("între sec. ii î. hr. şi 1/2 sec. iii");

        add("între 2004 - 2008");
        add("între sec. 2 a. chr. - 1/2 sec. 3");
        add("între 2004 – 2008");
        add("între sec. 2 a. chr. – 1/2 sec. 3");

        add("în intervalul 2004 și 2008");
        add("în intervalul sec. 2 a. chr. și 1/2 sec. 3");
        add("în intervalu 2004 și 2008");
        add("în intervalu sec. 2 a. chr. și 1/2 sec. 3");
        add("în interval 2004 și 2008");
        add("în interval sec. 2 a. chr. și 1/2 sec. 3");
        add("IN INTERVAL 2004 SI 2008");

        add("în intervalul 2004 - 2008");
        add("în intervalu 2004 - 2008");
        add("în interval 2004 - 2008");
        add("IN INTERVAL 2004 - 2008");

        add("în intervalul 2004 – 2008");
        add("în intervalu 2004 – 2008");
        add("în interval 2004 – 2008");
        add("IN INTERVAL 2004 – 2008");

        add("Sf. anului 1990");
        add("Înc. anului 1990");
        add("Sfârșit de an 1990");
        add("Început de an 1990");
        add("Sfârșitul anului 1990");
        add("Începutul anului 1990");
        add("Înc de an 1990 - sfârșitul anului 1995");
        add("Între anii 1990 - 1995");
    }};

    public static final List<String> MILLENNIUM_TIMESPAN_LIST = new ArrayList<>() {{
        add("între 2004 şi 2008"); // S with cedilla
        add("între 2004 și 2008"); // S with comma below.
        add("INTRE 2004 SI 2008");
        add("între mil. al 2-lea a. chr. şi al 3-lea");
        add("între mil. al ii-lea a. chr. şi al iii-lea");
        add("între mil. al 2-lea a. chr. şi 3");
        add("între mil. al ii-lea a. chr. şi iii");
        add("între mil. 2 a. chr. şi 3");
        add("între mil. ii a. chr. şi iii");
        add("între mil. 2-lea a. chr. şi 3");
        add("între mil. ii-lea a. chr. şi iii");
        add("între mil. 2 a. chr. şi 1/2 mil. 3");
        add("între mil. ii a. chr. şi 1/2 mil. iii");
        add("între mil. 4 şi 1/2 mil. 2");
        add("între mil. iv şi 1/2 mil. ii");
        add("între mil. 4 şi 1/2 mil. 2 a. chr");
        add("între mil. iv şi 1/2 mil. ii a. chr");
        add("între mil. 3 şi 1/2 mil. 2 p. chr");
        add("între mil. iii şi 1/2 mil. ii p. chr");

        add("între mil. 2 î. chr. şi 1/2 mil. 3");
        add("între mil. ii î. chr. şi 1/2 mil. iii");
        add("între mil. 2 î. hr. şi 1/2 mil. 3");
        add("între mil. ii î. hr. şi 1/2 mil. iii");

        add("între 2004 - 2008");
        add("între mil. 2 a. chr. - 1/2 mil. 3");
        add("între 2004 – 2008");
        add("între mil. 2 a. chr. – 1/2 mil. 3");

        add("în intervalul 2004 și 2008");
        add("în intervalul mil. 2 a. chr. și 1/2 mil. 3");
        add("în intervalu 2004 și 2008");
        add("în intervalu mil. 2 a. chr. și 1/2 mil. 3");
        add("în interval 2004 și 2008");
        add("în interval mil. 2 a. chr. și 1/2 mil. 3");
        add("IN INTERVAL 2004 SI 2008");

        add("în intervalul 2004 - 2008");
        add("în intervalu 2004 - 2008");
        add("în interval 2004 - 2008");
        add("IN INTERVAL 2004 - 2008");

        add("în intervalul 2004 – 2008");
        add("în intervalu 2004 – 2008");
        add("în interval 2004 – 2008");
        add("IN INTERVAL 2004 – 2008");
    }};

    public static final List<String> TIMESPAN_LIST = new ArrayList<>() {{
        addAll(CENTURY_TIMESPAN_LIST);
        addAll(MILLENNIUM_TIMESPAN_LIST);
    }};
}
