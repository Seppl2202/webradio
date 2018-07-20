package de.dhbw.webradio.test.m3uparser;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.m3uparser.URLTypeParser;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class URLTypeParserTest {

    @Test
    public void parseByContentDetection() throws MalformedURLException {
        URL url1 = new URL("http://stream.antenne.com/hra-nds/mp3-128/IMDADevice/");
        URL url2 = new URL("http://streams.bigfm.de/bigfm-charts-128-aac?usid=0-0-H-A-D-30");
        URLTypeParser parser = new URLTypeParser();
        assertEquals(FileExtension.MP3, parser.parseByContentDetection(url1));
        assertEquals(FileExtension.AAC, parser.parseByContentDetection(url2));
    }
}
