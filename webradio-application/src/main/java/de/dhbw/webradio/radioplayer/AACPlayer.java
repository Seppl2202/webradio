package de.dhbw.webradio.radioplayer;


import net.sourceforge.jaad.aac.AACException;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.adts.ADTSDemultiplexer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.URL;

public class AACPlayer extends AbstractPlayer implements Runnable {
    private Thread runner = new Thread(this);

    @Override
    public void setInputStream() {

    }

    @Override
    public void fetchStreamInfo() {

    }

    @Override
    public void play() {
        stop = false;
        if (!runner.isAlive()) {
            runner = new Thread(this);
            runner.start();
        }
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public void run() {
        decodeAndPlayAAC();
    }

    private void decodeAndPlayAAC() {
        SourceDataLine line = null;
        byte[] b;
        try {
            isPlaying = true;
            final ADTSDemultiplexer adts = new ADTSDemultiplexer(url.openStream());
            final Decoder dec = new Decoder(adts.getDecoderSpecificInfo());
            final SampleBuffer buf = new SampleBuffer();
            while (!stop) {
                b = adts.readNextFrame();
                dec.decodeFrame(b, buf);

                if (line == null) {
                    final AudioFormat aufmt = new AudioFormat(buf.getSampleRate(), buf.getBitsPerSample(), buf.getChannels(), true, true);
                    line = AudioSystem.getSourceDataLine(aufmt);
                    line.open();
                    line.start();
                }
                b = buf.getData();
                line.write(b, 0, b.length);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (AACException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.stop();
                line.close();
                isPlaying = false;
            }
        }
    }
}
