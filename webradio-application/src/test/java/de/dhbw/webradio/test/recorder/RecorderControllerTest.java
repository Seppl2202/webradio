package de.dhbw.webradio.test.recorder;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.recording.AACRecorder;
import de.dhbw.webradio.recording.MP3Recorder;
import de.dhbw.webradio.recording.Recorder;
import de.dhbw.webradio.recording.RecorderController;
import de.dhbw.webradio.settings.SettingsParser;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class RecorderControllerTest {

    public RecorderController r = RecorderController.getInstance();

    @Before
    public void init() {
        WebradioPlayer.parseSettings(new SettingsParser());
    }

    @Test
    public void getInstance() {
        assertTrue(RecorderController.getInstance() instanceof RecorderController);
    }

    @Test
    public void recordNow() throws MalformedURLException {

        Recorder recorder1 = r.recordNow(new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
        Recorder recorder2 = r.recordNow(new URL("http://mp3.ffh.de/radioffh/hqlivestream.aac"));

        assertTrue(recorder1 instanceof Recorder && recorder2 instanceof Recorder);
        assertTrue(recorder1 instanceof MP3Recorder);
        assertTrue(recorder2 instanceof AACRecorder);
    }

    @Test
    public void getActualRecordNowRecorder() throws MalformedURLException {
        r.setActualRecordNowRecorder(r.recordNow(new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3")));
        assertTrue(r.getActualRecordNowRecorder() instanceof MP3Recorder);
    }

    @Test
    public void setActualRecordNowRecorder() {

    }

    @Test
    public void getScheduledRecordList() {
    }

    @Test
    public void addScheduledRecord() {
    }

    @Test
    public void removeScheduledRecord() {
    }
}
