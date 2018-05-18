package de.dhbw.webradio.test;

import de.dhbw.webradio.m3uparser.M3uParser;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class M3uParserTest {
    File success = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_success.m3u");
    File fail = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_fail.m3u");
    M3uParser m3uParser = new M3uParser();
    String expectedText = "#EXTM3U\r\n" +
            "#EXTINF:-1,SWR1 Baden-Württemberg\r\n" +
            "http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3\r\n";

    @Test
    public void parseFileToString() {

        String parsedText = null;

        try {
            assertEquals(expectedText, m3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseUrlFromString() {
        String urlInfoSuccess[] = null;
        String urlInfoFail[] = null;
        try {
            urlInfoSuccess = m3uParser.parseUrlFromString(m3uParser.parseFileToString(success));
            urlInfoFail = m3uParser.parseUrlFromString(m3uParser.parseFileToString(fail));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        }
        //test m3u with valid EXTINF tag and corresponding URL
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", urlInfoSuccess[0]);
        //test m3u without EXTINF tag
        assertEquals("#EXTM3U", urlInfoFail[0]);
    }

    @Test
    public void parseFileFromUrlToString() {
        URL url = null;
        String s = null;
        try {
            url = new URL("http://mp3-live.swr.de/swr1bw_m.m3u");
            s = m3uParser.parseFileFromUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assertEquals(expectedText, s);
    }
}
