package de.dhbw.webradio.recording;

import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.adts.ADTSDemultiplexer;
import net.sourceforge.jaad.util.wav.WaveFileWriter;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AACRecorder implements Recorder, Runnable {
    private WaveFileWriter waveFileWriter;
    private boolean stop = false;
    private SampleBuffer buf;
    private URL url;
    private File fileToSave;

    public AACRecorder(URL urlToRecord, File file) {
        setUrl(urlToRecord);
        setFile(file);
    }

    @Override
    public void recordNow() throws LineUnavailableException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    final ADTSDemultiplexer adts = new ADTSDemultiplexer(con.getInputStream());
                    final Decoder dec = new Decoder(adts.getDecoderSpecificInfo());
                    buf = new SampleBuffer();
                    byte[] b;
                    int i = 0;
                    System.err.println("starting record: " + url + adts.getChannelCount());
                    while (!stop) {
                        b = adts.readNextFrame();
                        dec.decodeFrame(b, buf);
                        if (waveFileWriter == null)
                            waveFileWriter = new WaveFileWriter(fileToSave, buf.getSampleRate(), buf.getChannels(), buf.getBitsPerSample());
                        waveFileWriter.write(buf.getData());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (waveFileWriter != null) {
                        try {
                            waveFileWriter.close();
                            System.err.println("finished");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void saveToFile() throws IOException {
        waveFileWriter.write(buf.getData());
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
