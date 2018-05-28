package de.dhbw.webradio.recording;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public interface Recorder {
    public void recordNow() throws LineUnavailableException, IOException;

    public void saveToFile() throws IOException;

    public void recordByTitle();

    public void stop();
}
