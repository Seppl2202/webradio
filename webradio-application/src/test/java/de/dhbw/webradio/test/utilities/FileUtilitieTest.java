package de.dhbw.webradio.test.utilities;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;
import de.dhbw.webradio.radioplayer.MP3Player;
import de.dhbw.webradio.utilities.FileUtilitie;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileUtilitieTest {

    @Test
    public void generateFileNameForRecording() throws IOException, NoSuchFieldException, IllegalAccessException {
        IcyInputStreamReader r = new IcyInputStreamReader(new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
        Field f = r.getClass().getDeclaredField("id3Values");
        f.setAccessible(true);
        Map<String, String> values = new HashMap<>();
        AbstractPlayer p = new MP3Player();
        p.setIcyReader(r);
        WebradioPlayer.setPlayer(p);
        values.put("titleInfo", "TesttitelMitSönderzéichen");
        f.set(r, values);
        assertEquals("TesttitelMitSnderzichen", FileUtilitie.generateFileNameForRecording());
    }
}
