package de.dhbw.webradio.test;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class FileExtensionParserTest {

    FileExtensionParser fileExtensionParser = new FileExtensionParser();
    @Test
    public void testParsing() throws MalformedURLException{
        URL url = new URL("http://mp3-live.swr.de/swr1bw_m.m3u");
        assertEquals(FileExtension.M3U, fileExtensionParser.parseFileExtension(url));
        URL url2 = new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3");
        assertEquals(FileExtension.MP3, fileExtensionParser.parseFileExtension(url2));
        assertEquals(FileExtension.UNSUPPORTED_TYPE, fileExtensionParser.parseFileExtension(new URL("aacsdfdsfdsfdss.aac")));
    }

}
