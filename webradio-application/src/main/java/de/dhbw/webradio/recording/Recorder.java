package de.dhbw.webradio.recording;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;

public interface Recorder {

    public void recordNow(URL url) throws LineUnavailableException, IOException;


    public void recordByTitle();

    public void stop();

    public boolean isRecording();
}
