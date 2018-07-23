package de.dhbw.webradio.test.radioplayer;

import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IcyInputStreamReaderTest {

    @Test
    public void getActualTitle() throws IOException, InterruptedException {
        IcyInputStreamReader r = new IcyInputStreamReader(new URL("http://swr-swr3-live.cast.addradio.de/swr/swr3/live/mp3/128/stream.mp3"));
        Thread t = new Thread(r);
        t.start();
        Thread.sleep(5000);
        assertEquals("SWR3", r.getStationName());
    }

    @Test
    public void matchesScheduledRecord() throws IOException, NoSuchFieldException, IllegalAccessException {
        ScheduledRecord r = new ScheduledRecord("Testtitel", "Testinterpret");
        IcyInputStreamReader reader = new IcyInputStreamReader(new URL("http://swr-swr3-live.cast.addradio.de/swr/swr3/live/mp3/128/stream.mp3"));
        Field f = reader.getClass().getDeclaredField("id3Values");
        f.setAccessible(true);
        Map<String, String> id3Values = (Map<String, String>) f.get(reader);
        id3Values.put("titleInfo", "Testtitel /Testinterpret");
        f.set(reader, id3Values);
        assertTrue(reader.matchesScheduledRecord(r));

        id3Values.put("titleInfo", "Testtitel\\Testinterpret");
        ScheduledRecord r2 = new ScheduledRecord("Testtitel", "Testinterpret");
        f.set(reader, id3Values);
        assertTrue(reader.matchesScheduledRecord(r2));

        ScheduledRecord r3 = new ScheduledRecord("asdasasdf", "sfnsdjkfh");
        assertTrue(!reader.matchesScheduledRecord(r3));


    }
}
