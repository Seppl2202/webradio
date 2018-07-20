package de.dhbw.webradio.test.radioplayer;

import de.dhbw.webradio.radioplayer.IcyInputStreamReader;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class IcyInputStreamReaderTest {

    @Test
    public void getActualTitle() throws IOException, InterruptedException {
        IcyInputStreamReader r = new IcyInputStreamReader(new URL("http://swr-swr3-live.cast.addradio.de/swr/swr3/live/mp3/128/stream.mp3"));
        Thread t = new Thread(r);
        t.start();
        Thread.sleep(5000);
        assertEquals("SWR3", r.getStationName());
    }
}
