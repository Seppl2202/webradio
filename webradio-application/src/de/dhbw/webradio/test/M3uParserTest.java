package de.dhbw.webradio.test;

import de.dhbw.webradio.m3uparser.M3uParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class M3uParserTest {
    File success = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_success.m3u");
    File fail = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_fail.m3u");
    M3uParser m3uParser = new M3uParser();

    @Test
    public void parseFileToString() {

        String parsedText = null;
        String expectedText = "#EXTM3U\r\n" +
                "#EXTINF:-1,SWR1 Baden-Württemberg\r\n" +
                "http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3\r\n";
        try {
            assertEquals(expectedText, m3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseUrlFromString() {
        String urlSuccess = null;
        String urlFail = null;
        try {
            urlSuccess = m3uParser.parseUrlFromString(m3uParser.parseFileToString(success));
            urlFail = m3uParser.parseUrlFromString(m3uParser.parseFileToString(fail));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //test m3u with valid EXTINF tag and corresponding URL
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", urlSuccess);
        //test m3u without EXTINF tag
        assertEquals("#EXTM3U", urlFail);
    }
}
