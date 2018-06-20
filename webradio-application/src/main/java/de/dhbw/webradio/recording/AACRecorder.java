package de.dhbw.webradio.recording;

import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.util.wav.WaveFileWriter;
import org.joda.time.DateTime;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AACRecorder implements Recorder, Runnable {
    private boolean stop = false;
    private URL url;
    private File fileToSave;

    public AACRecorder(URL urlToRecord, File file) {
        setUrl(urlToRecord);
        setFile(file);
    }

    @Override
    public void recordNow(URL urlt, String filename) throws LineUnavailableException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream in = con.getInputStream();
                    File f = new File("C://repository/recaac" + System.currentTimeMillis() + ".aac");
                    OutputStream out = new FileOutputStream(f);
                    byte[] buffer = new byte[4096];
                    int len;
                    long t = System.currentTimeMillis();
                    System.err.println("entering while");
                    while ((len = in.read(buffer)) > 0 && (!stop)) {
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
                }
            }
        }).start();
    }

    @Override
    public void saveToFile() throws IOException {
    }

    @Override
    public void recordByTitle() {

    }


    @Override
    public void run() {

    }

    @Override
    public void stop() {
        this.stop = true;
        System.err.println("stopped");
    }

    public void setUrl(URL url) {
        if (url != null) {
            this.url = url;
        }
    }

    public void setFile(File file) {
        this.fileToSave = file;
    }
}
