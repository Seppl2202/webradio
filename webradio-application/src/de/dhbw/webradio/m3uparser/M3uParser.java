package de.dhbw.webradio.m3uparser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class M3uParser {
    private final static String START_TOKEN = "#EXTM3U";
    private final static String INFO_TOKEN = "#EXTINF:";
    private final static String URL_TOKEN = "http://";

    public static String parseFileToString(File m3ufile) throws IOException {

        StringBuilder fileContent = new StringBuilder((int) m3ufile.length());
        Scanner scanner = new Scanner(m3ufile);
        String lineSeperator = System.lineSeparator();

        try {
            while (scanner.hasNext()) {
                fileContent.append(scanner.nextLine() + lineSeperator);
            }
            return fileContent.toString();
        } finally {
            scanner.close();
        }
    }


    public static String parseUrlFromString(String s) {
        String[] splittedLines = s.split("\r\n");
        int urlLine = 0;
        for (int i = 0; i < splittedLines.length; i++) {
            if (splittedLines[i].contains(URL_TOKEN)) {
                urlLine = i;
            }
        }
        return splittedLines[urlLine];
    }
}
