package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.logger.Logger;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Mp3Player extends AbstractPlayer implements Runnable {
    private static int BufferSize = 1024; // Anzahl der Daten, die aufeinmal an die Soundkarte geschickt werden.
    private static byte[] buffer = new byte[BufferSize];
    private Thread runner = new Thread(this); //AbspielThread
    private AudioFormat audioFormat;


    /**
     * starten der Wiedergabe
     */
    public void play() {
        stop = false;
        if (!runner.isAlive()) {
            runner.start();
        }
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
        if (url != null) {
            setInputStream();
        }
    }

    public void setInputStream() {
        try {
            ais = AudioSystem.getAudioInputStream(url);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * der WiedergabeThread
     */
    public void run()

    {
        do {
            try {
                resetKorrektur = 0;
                AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, ais);
                AudioFormat audioFormat = in.getFormat();
                audioFormatChannels = audioFormat.getChannels();
                audioFormatFrameRate = audioFormat.getFrameRate();
                audioFormatFrameSize = audioFormat.getFrameSize();
                audioFormatSampleRate = audioFormat.getSampleRate();
                audioFormatSampleSizeInBits = audioFormat.getSampleSizeInBits();
                audioFormatEncoding = audioFormat.getEncoding().toString();
                audioFormatproperties = audioFormat.properties();
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
                line.open(audioFormat);
                FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                line.start();
                logAudioData(in, audioFormat, line);
                updateGui();
                sampleSizeInBits = audioFormat.getSampleSizeInBits();

                //mp3
                if (in.getFrameLength() == -1) {
                    bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
                }
                //wav
                else {
                    bitRate = (int) (in.available() / (in.getFrameLength() / (long) audioFormat.getFrameRate()) * 8 / 1000);
                }

                in.mark(in.available());
                logPlayStart();
                while ((true) && (!stop)) {
                    stop = false;
                    int gainLevel = (int) ((int) gainControl.getMinimum() + ((gainControl.getMaximum() - gainControl.getMinimum()) / 100 * gainPercent));
                    gainControl.setValue(gainLevel);
                    if (!pause) {
                        int n = in.read(buffer, 0, buffer.length);
                        if ((n < 0) || (stop)) {
                            break;
                        }
                        if (reset) {
                            resetKorrektur = line.getMicrosecondPosition() / 1000;
                            in.reset();
                            reset = false;
                        }
                        line.write(buffer, 0, n);

                    }
                }
                line.drain();
                line.close();
                in.close();

            } catch (IOException e) {
                System.out.println("Datei nicht gefunden" + e);
            } catch (LineUnavailableException e) {
                System.out.println("Soundkartenfehler");
            }
        } while (!stop);
    }

    private void updateGui() {
        GUIHandler.getInstance().updateAudioDetails(this);
    }

    private void logPlayStart() {
        String logInfo =
                "Started player: " + this.url;
        Logger.logInfo(logInfo);
    }

    private void logAudioData(AudioInputStream in, AudioFormat audioFormat, SourceDataLine line) throws IOException {
        String logInfo =
                "Number of channels: " + audioFormat.getChannels() + "\r\n" +
                        "Sample rate: " + audioFormat.getSampleRate() + "\r\n" +
                        "Frame size: " + audioFormat.getFrameSize() + "\r\n" +
                        "Encoding: " + audioFormat.getEncoding() + "\r\n" +
                        "audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits() + "\r\n" +
                        "audioFormat.getEncoding: " + audioFormat.getEncoding() + "\r\n" +
                        "audioFormat.properties: " + audioFormat.properties() + "\r\n" +
                        "audioFormat.getChannels: " + audioFormat.getChannels() + "\r\n" +
                        "div. by:  audioFormat.getFrameRate: " + audioFormat.getFrameRate() + "\r\n" +
                        "audioFormat.getFrameSize: " + audioFormat.getFrameSize() + "\r\n" +
                        "audioFormat.getSampleRate: " + audioFormat.getSampleRate() + "\r\n" +
                        "audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits() + "\r\n" +
                        "audioFormat.getEncoding: " + audioFormat.getEncoding() + "\r\n" +
                        "audioFormat.properties: " + audioFormat.properties() + "\r\n" +
                        "in.available: " + in.available() + "\r\n" +
                        "in.getFrameLength: " + in.getFrameLength() + "\r\n" +
                        "audioFormat.getFrameRate: " + audioFormat.getFrameRate() + "\r\n" +
                        "div. by: in.FrameLength: " + in.getFrameLength() + "\r\n" +
                        "in.getFormat: " + in.getFormat() + "\r\n" +
                        "line.available: " + line.available() + "\r\n" +
                        "line.getBufferSize: " + line.getBufferSize() + "\r\n" +
                        "line.available: " + line.getFramePosition() + "\r\n" +
                        "line.getFramePosition: " + line.getLevel() + "\r\n" +
                        "line.getLongFramePosition: " + line.getLongFramePosition() + "\r\n" +
                        "line.getMicrosecondPosition: " + line.getMicrosecondPosition() + "\r\n" +
                        "line.getFormat: " + line.getFormat() + "\r\n" +
                        "line.getLineInfo: " + line.getLineInfo();
        Logger.logInfo(logInfo);
    }

    public void fetchStreamInfo() {
        try {
            resetKorrektur = 0;
            AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(url));
            audioFormat = in.getFormat();
            audioFormatChannels = audioFormat.getChannels();
            audioFormatFrameRate = audioFormat.getFrameRate();
            audioFormatFrameSize = audioFormat.getFrameSize();
            audioFormatSampleRate = audioFormat.getSampleRate();
            audioFormatSampleSizeInBits = audioFormat.getSampleSizeInBits();
            audioFormatEncoding = audioFormat.getEncoding().toString();
            audioFormatproperties = audioFormat.properties();
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
            line.open(audioFormat);
            line.start();
            sampleSizeInBits = audioFormat.getSampleSizeInBits();

            if (in.getFrameLength() == -1) {
                bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
            } else {
                bitRate = (int) (in.available() / (in.getFrameLength() / (long) audioFormat.getFrameRate()) * 8 / 1000);
            }
            line.close();
            in.close();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("nicht unterstütztes Format");
        } catch (IOException e) {
            System.out.println("Datei nicht gefunden" + e);
        } catch (LineUnavailableException e) {
            System.out.println("Soundkartenfehler");
        }
        BufferSize = bitRate / 8 * 1000 / 10; // Buffergröße auf Anzahl der benötigten Bytes pro 1/10s
        buffer = new byte[BufferSize];

    }

    @Override
    public String toString() {
        return "URL: " + url;
    }
}
