package de.dhbw.webradio.test;


import de.dhbw.webradio.models.Station;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class StationTest {

    Station s1 = new Station("SWR1 BW", new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
    Station s2 = new Station("not working test", new URL("http://www.testurl.de/notworkingurl"));
    public StationTest() throws MalformedURLException {
    }

    @Test
    public void getName() {
        assertEquals("SWR1 BW", s1.getName());
    }

    @Test
    public void setName() {
        s1.setName("SWR1 BW NEW_NAME");
        assertEquals("SWR1 BW NEW_NAME", s1.getName());
    }

    @Test
    public void getStationURL() {
        assertEquals("http://mp3-live.swr.de/swr1bw_m.m3u", s1.getStationURL().toString());
    }

    @Test
    public void setStationURL() throws MalformedURLException {
        s2.setStationURL(new URL("http://www.abcdefg.de/gjzsdgfh"));
    }

    @Test
    public void isURLValid() throws IOException {
        assertEquals(true, s1.isURLValid());
        assertEquals(false, s2.isURLValid());
    }

    @Test
    public void getM3uStationName() {
        s1.setM3uStationName("SWR 1");
        assertEquals("SWR 1", s1.getM3uStationName());
    }

    @Test
    public void setM3uStationName() {
        s1.setM3uStationName("SWR1");
        assertEquals("SWR1", s1.getM3uStationName());
    }

    @Test
    public void equals() {
        assertEquals(false, s1.equals(s2));
    }
}
