package de.dhbw.webradio.test.m3uparser;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.playlistparser.FileExtensionParser;
import org.junit.Test;

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
    }

}
