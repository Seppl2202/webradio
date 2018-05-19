package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import org.apache.commons.io.FileUtils;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class M3uParser {
    private final static String START_TOKEN = "#EXTM3U";
    private final static String INFO_TOKEN = "#EXTINF:";
    private final static String URL_TOKEN = "http://";

    public String parseFileToString(File m3ufile) throws IOException {

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

    public String parseFileFromUrlToString(URL url) throws IOException {
        File f = File.createTempFile("temp", ".m3u");
        FileUtils.copyURLToFile(url, f);
        return parseFileToString(f);
    }


    /**
     * @param s the string to be parsed
     * @return an array of m3u informations. 0: the mp3 url, 1: the additional information of the #EXTINF
     * @throws UnsupportedAudioFileException if the passed string does not match the specified extended m3u syntax.
     * See @https://de.wikipedia.org/wiki/M3U#Erweiterte_M3U for further information
     */
    public String[] parseUrlFromString(String s) throws UnsupportedAudioFileException, NoURLTagFoundException {
        String[] splittedLines = s.split("\r\n");
        int urlLine = 0;
        if (!(splittedLines[0].contains(START_TOKEN))) {
            throw new UnsupportedAudioFileException("File did not contain a valid extended M3U syntax");
        }
        for (int i = 0; i < splittedLines.length; i++) {
            if (splittedLines[i].contains(URL_TOKEN)) {
                urlLine = i;
            }
        }
        String[] finalInfo = new String[2];
        if(urlLine == 0) {
            throw new NoURLTagFoundException(s);
        }
        finalInfo[0] = splittedLines[urlLine];
        finalInfo[1] = splittedLines[urlLine - 1].substring(7); //#EXTINF tags ends at the seventh letter
        return finalInfo;
    }
}
