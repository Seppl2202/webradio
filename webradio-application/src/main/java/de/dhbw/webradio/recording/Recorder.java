package de.dhbw.webradio.recording;

import de.dhbw.webradio.radioplayer.MetainformationReader;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;

public interface Recorder {

    public void recordNow(URL url);


    public void recordByTitle(URL url, Recorder recorder);

    public void stop();

    public boolean isRecording();

    public void setMetaInformationReader(MetainformationReader r);
}
