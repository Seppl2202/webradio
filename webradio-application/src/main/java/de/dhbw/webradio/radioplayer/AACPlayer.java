package de.dhbw.webradio.radioplayer;


import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.recording.AACRecorder;
import net.sourceforge.jaad.aac.AACException;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.adts.ADTSDemultiplexer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AACPlayer extends AbstractPlayer implements Runnable {
    private Thread runner = new Thread(this);
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
        gainPercent = 90;
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
                FloatControl gainControl = null;

                if (line == null) {
                    final AudioFormat audioFormat = new AudioFormat(buf.getSampleRate(), buf.getBitsPerSample(), buf.getChannels(), true, true);
                    line = AudioSystem.getSourceDataLine(audioFormat);
                    line.open();
                    gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                    line.start();
                    addAudioDetails(audioFormat);
                }
                b = buf.getData();
                line.write(b, 0, b.length);

//                float range = gainControl.getMaximum() - gainControl.getMinimum();
//                float gain = (range * (gainPercent/100)) + gainControl.getMinimum();
//                gainControl.setValue(gain);

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
                GUIHandler.getInstance().resetComponents();
            }
        }
    }

    private void addAudioDetails(AudioFormat audioFormat) {
        audioFormatChannels = audioFormat.getChannels();
        audioFormatEncoding = audioFormat.getEncoding().toString();
        audioFormatSampleRate = audioFormat.getSampleRate();
        GUIHandler.getInstance().updateAudioDetails(this);
    }
}
