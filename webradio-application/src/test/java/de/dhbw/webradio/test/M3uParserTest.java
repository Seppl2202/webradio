package de.dhbw.webradio.test;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.m3uparser.M3uParser;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class M3uParserTest {
    File success = new File("C:\\repository\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\testm3uparsing_success.m3u");
    File fail = new File("C:\\repository\\webradio\\webradio-application\\src\\test\\java\\de\\dhbw\\webradio\\test\\testfiles\\testm3uparsing_fail.m3u");
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
        try {
            urlInfoSuccess = m3uParser.parseUrlFromString(m3uParser.parseFileToString(success));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (NoURLTagFoundException nufe) {
            nufe.printStackTrace();
        }
        //test m3u with valid EXTINF tag and corresponding URL
        assertEquals("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3", urlInfoSuccess[0]);
    }

    @Test(expected = NoURLTagFoundException.class)
    public void parseURLFromString() {
        String urlInfoFail[] = null;
        try {
            urlInfoFail = m3uParser.parseUrlFromString(m3uParser.parseFileToString(fail));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (NoURLTagFoundException nufe) {
            nufe.printStackTrace();
        }
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

    @Test
    public void testM3uInfo() {
        String s = null;
        try {
            URL url = new URL("http://mp3-live.swr.de/swr1bw_m.m3u");
            s = m3uParser.parseFileFromUrlToString(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] finalInfo = new String[2];
        try {
            finalInfo = m3uParser.parseUrlFromString(s);
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (NoURLTagFoundException nufe) {
            nufe.printStackTrace();
        }
        //testb m3u media name parsing
        assertEquals("SWR1 Baden-Württemberg", finalInfo[1]);


    }
}
