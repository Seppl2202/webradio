package de.dhbw.webradio.test.m3uparser;

import de.dhbw.webradio.playlistparser.PLSParser;
import de.dhbw.webradio.models.InformationObject;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PLSParserTest {
    @Test
    public void parsePLS() throws MalformedURLException {
        URL url = new URL("http://main-aacp.rautemusik.fm/listen.pls");
        PLSParser p = new PLSParser();
        List<InformationObject> plsEntries = p.parsePLS(url);
        assertEquals(1, plsEntries.size());
        assertEquals("http://main-aacp.rautemusik.fm", plsEntries.get(0).getUrl().toString());
    }
}
