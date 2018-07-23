package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.radioplayer.MetainformationReader;
import de.dhbw.webradio.utilities.FileUtilitie;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.util.wav.WaveFileWriter;
import org.joda.time.DateTime;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.WeakHashMap;

public class AACRecorder implements Recorder, Runnable {
    private boolean recording = false;
    private File recorderDirectory = WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory();
    private MetainformationReader reader = null;

    public AACRecorder() {
    }

    @Override
    public void recordNow(URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream in = con.getInputStream();
                    File f = new File(recorderDirectory + "/" + generateFileName() + ".aac");
                    Path path = Paths.get(f.toURI());
                    OutputStream out = new FileOutputStream(path.toFile());
                    byte[] buffer = new byte[4096];
                    int len;
                    long t = System.currentTimeMillis();
                    System.err.println("entering while");
                    recording = true;
                    while ((len = in.read(buffer)) > 0 && (recording)) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    System.err.println("finished");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stop();
                }
            }
        }).start();
    }
    private String generateFileName() {
        //remove everything but characters and numbers
        return FileUtilitie.generateFileNameForRecording();
    }


    @Override
    public void recordByTitle(URL url) {

    }


    @Override
    public void run() {

    }

    @Override
    public void stop() {
        this.recording = false;
        System.err.println("stopped");
    }

    @Override
    public boolean isRecording() {
        return recording;
    }

    @Override
    public void setMetaInformationReader(MetainformationReader r) {
        this.reader = r;
    }
}
