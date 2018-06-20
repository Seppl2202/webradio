package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.models.M3UInfo;
import org.apache.commons.io.FileUtils;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class M3uParser {
    private final static String START_TOKEN = "#EXTM3U";
    private final static String INFO_TOKEN = "#EXTINF:";
    private final static String URL_TOKEN = "http://";
    private static int MAX_REDIRECT_COUNT = 5;
    private int redirectCount = 0;

    /**
     * @param m3ufile the temporary downloaded m3ufile
     * @return a string of the parsed text. no check for syntax! syntax check is done in parseURL
     * @throws IOException if the file does not exists or any file system error occurs
     */
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


    /**
     * Parses a file of an URL to String
     *
     * @param url the file url
     * @return the parsed string
     * @throws IOException           if an error occurs during writing the stream
     * @throws MalformedURLException if the url contains too many redirects (specified by MAX_REDIRECT_COUNT)
     */
    public String parseFileFromUrlToString(URL url) throws IOException, MalformedURLException {
        File f = File.createTempFile("temp", ".m3u");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.connect();
        int statusCode = con.getResponseCode();
        if (statusCode == 200 || statusCode == 201) {
            FileUtils.copyURLToFile(url, f);
            return parseFileToString(f);

        }
        String newURL = con.getHeaderField("Location");
        if (redirectCount == MAX_REDIRECT_COUNT) {
            throw new MalformedURLException("URL " + url + " contains too many redirects: \r\n" +
                    "Last known redirect points to: " + newURL);
        }
        redirectCount++;
        return parseFileFromUrlToString(new URL(newURL));
    }

    public List<M3UInfo> parseUrlFromString(String s) throws NoURLTagFoundException, UnsupportedAudioFileException, MalformedURLException {
        if (s.contains(START_TOKEN)) {
            return parseUrlFromEM3UString(s);
        }
        return parseUrlFromSM3U(s);
    }

    /**
     * @param s the string to be parsed
     * @return an array of m3u informations. 0: the mp3 url, 1: the additional information of the #EXTINF
     * @throws UnsupportedAudioFileException if the passed string does not match the specified extended m3u syntax.
     *                                       See @https://de.wikipedia.org/wiki/M3U#Erweiterte_M3U for further information
     */
    private List<M3UInfo> parseUrlFromEM3UString(String s) throws UnsupportedAudioFileException, NoURLTagFoundException {
        String[] splittedLines = s.split("\r\n");
        List<M3UInfo> m3UInfos = new ArrayList<>();
        int urlLine = 0;
        if (!(splittedLines[0].contains(START_TOKEN))) {
            throw new UnsupportedAudioFileException("File did not contain a valid extended M3U syntax");
        }
        for (int i = 0; i < splittedLines.length; i++) {
            if (splittedLines[i].contains(URL_TOKEN)) {
                urlLine = i;
                try {
                    URL url = new URL(splittedLines[urlLine]);
                    String title = splittedLines[urlLine - 1].split(",")[1];
                    m3UInfos.add(new M3UInfo(url, title));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (urlLine == 0) {
            throw new NoURLTagFoundException(s);
        }
        return m3UInfos;
    }

    private List<M3UInfo> parseUrlFromSM3U(String s) throws MalformedURLException, NoURLTagFoundException {
        List<M3UInfo> m3uInfos = new ArrayList<>();
        String[] splittedLines = s.split("\r\n");
        for (int i = 0; i < splittedLines.length; i++) {
            M3UInfo info = new M3UInfo(new URL(splittedLines[i]), "Nicht verfügbar");
            m3uInfos.add(info);
        }
        if (m3uInfos.size() == 0) {
            throw new NoURLTagFoundException("Simple M3U did not contain valid URLs");
        }
        return m3uInfos;
    }

    public int getRedirectCount() {
        return redirectCount;
    }

    /**
     * Change the redirect limit
     * ATTENTION: Long waiting and/or endless loops may result!
     * @param redirectCount the new redirect limit
     */
    public void setRedirectCount(int redirectCount) {
        this.redirectCount = redirectCount;
    }
}
