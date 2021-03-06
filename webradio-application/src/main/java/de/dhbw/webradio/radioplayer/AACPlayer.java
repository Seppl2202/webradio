package de.dhbw.webradio.radioplayer;


import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.logger.Logger;
import net.sourceforge.jaad.aac.AACException;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.adts.ADTSDemultiplexer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AACPlayer extends AbstractPlayer implements Runnable {
    private Thread thread = new Thread(this);

    @Override
    public void play() {
        stop = false;
        if (!thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
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
            stop = false;
            final ADTSDemultiplexer adts = new ADTSDemultiplexer(url.openStream());
            final Decoder dec = new Decoder(adts.getDecoderSpecificInfo());
            final SampleBuffer buf = new SampleBuffer();
                FloatControl gainControl = null;
            while (!stop) {
                b = adts.readNextFrame();
                //here the AACException for unexpected profile is thrown
                dec.decodeFrame(b, buf);

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

                int gainLevel = (int) ((int) gainControl.getMinimum() + ((gainControl.getMaximum() - gainControl.getMinimum()) / 100 * gainPercent));
                gainControl.setValue(gainLevel);

            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (AACException e) {
            Logger.logError("AACException catched. This is a known problem. Please retry with another stream until the player starts correctly.");
            e.printStackTrace();
            WebradioPlayer.getPlayer().getIcyReader().setInterrupted(true);
            WebradioPlayer.setPlayer(null);
            GUIHandler.getInstance().resetComponents();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.stop();
                line.close();
                stop = true;
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

    @Override
    public String toString() {
        return "URL: " + url;
    }
}
