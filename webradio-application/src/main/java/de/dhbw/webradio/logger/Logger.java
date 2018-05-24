package de.dhbw.webradio.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {
    public static void logInfo(String info) {
        System.out.println("---" + getActualDateAsString() + "---" + "\r\n"
                + info);
    }

    public static void logError(String error) {
        System.err.println("---" + getActualDateAsString() + "---" + "\r\n"
                + error);
    }

    private static String getActualDateAsString() {
        DateFormat df = new SimpleDateFormat("MM-dd-yyy HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return df.format(now);
    }
}
