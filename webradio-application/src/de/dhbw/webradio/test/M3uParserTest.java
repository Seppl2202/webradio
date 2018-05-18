package de.dhbw.webradio.test;

import de.dhbw.webradio.m3uparser.M3uParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class M3uParserTest {
    File success = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_success.m3u");
    File fail = new File("C://repository/webradio/webradio-application/src/de/dhbw/webradio/test/testfiles/testm3uparsing_fail.m3u");

    @Test
    public void parseFileToString() {

        String parsedText = null;
        String expectedText = "#EXTM3U\r\n" +
                "#EXTINF:-1,SWR1 Baden-Württemberg\r\n" +
                "http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3\r\n";
        try {
            assertEquals(expectedText, M3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseUrlFromString() {
        String urlSuccess = null;
        String urlFail = null;
        try {
            urlSuccess = M3uParser.parseUrlFromString(M3uParser.parseFileToString(success));
            urlFail = M3uParser.parseUrlFromString(M3uParser.parseFileToString(fail));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //test m3u with valid EXTINF tag and corresponding URL
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", urlSuccess);
        //test m3u without EXTINF tag
        assertEquals("#EXTM3U", urlFail);
    }
}
