package de.dhbw.webradio.test;

import de.dhbw.webradio.models.M3UInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class M3UInfoTest {

    public M3UInfo info1 = new M3UInfo(new URL("http://testurl1.de/audio.mp3"), "test1");
    public M3UInfo info2 = new M3UInfo(new URL("http://testurl2.de/audio.aac"), "test2");
    public M3UInfo info3 = new M3UInfo(new URL("http://testurl1.de/audio.mp3"), "test1");


    public M3UInfoTest() throws MalformedURLException {
    }

    @Test
    public void getUrl() {
        assertEquals("http://testurl1.de/audio.mp3", info1.getUrl().toString());
        assertEquals("http://testurl2.de/audio.aac", info2.getUrl().toString());
    }

    @Test
    public void getTitleInfo() {
        assertEquals("test1", info1.getTitleInfo());
        assertEquals("test2", info2.getTitleInfo());
    }

    @Test
    public void equals() {
        assertEquals(false, info1.equals(info2));
        assertEquals(true, info1.equals(info1));
        assertEquals(true, info1.equals(info3));
    }
}
