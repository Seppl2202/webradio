package de.dhbw.webradio.radioplayer;

import de.dhbw.webradio.WebradioPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Mp3Player extends AbstractPlayer implements Runnable {
    private static int BufferSize = 1024; // Anzahl der Daten, die aufeinmal an die Soundkarte geschickt werden.
    private static byte[] buffer = new byte[BufferSize];
    private Thread runner = new Thread(this); //AbspielThread

    /**
     * starten der Wiedergabe
     */
    public void play() {
        stop = false;
        if (!runner.isAlive()) {
            runner = new Thread(this);
            runner.start();
        }
    }

    @Override
    public void setUrl(URL url) {
        this.song = new File("");
        this.url = url;
        if (url != null) {
            setInputStream();
        }
    }

    @Override
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

                System.out.println("audioFormat.getChannels: " + audioFormat.getChannels());
                System.out.println("audioFormat.getFrameSize: " + audioFormat.getFrameSize());
                System.out.println("audioFormat.getSampleRate: " + audioFormat.getSampleRate());
                System.out.println("audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits());
                System.out.println("audioFormat.getEncoding: " + audioFormat.getEncoding());
                System.out.println("audioFormat.properties: " + audioFormat.properties());
                System.out.println("audioFormat.getChannels: " + audioFormat.getChannels());
                System.out.println("durch:  audioFormat.getFrameRate: " + audioFormat.getFrameRate());
                System.out.println("audioFormat.getFrameSize: " + audioFormat.getFrameSize());
                System.out.println("audioFormat.getSampleRate: " + audioFormat.getSampleRate());
                System.out.println("audioFormat.getSampleSizeInBits: " + audioFormat.getSampleSizeInBits());
                System.out.println("audioFormat.getEncoding: " + audioFormat.getEncoding());
                System.out.println("audioFormat.properties: " + audioFormat.properties());
                System.out.println("in.available: " + in.available());
                System.out.println("in.getFrameLength: " + in.getFrameLength());
                System.out.println("audioFormat.getFrameRate: " + audioFormat.getFrameRate());
                System.out.println("dies geteilt: in.FrameLength: " + in.getFrameLength());
                System.out.println("in.getFormat: " + in.getFormat());
                System.out.println("line.available: " + line.available());
                System.out.println("line.getBufferSize: " + line.getBufferSize());
                System.out.println("line.available: " + line.getFramePosition());
                System.out.println("line.getFramePosition: " + line.getLevel());
                System.out.println("line.getLongFramePosition: " + line.getLongFramePosition());
                System.out.println("line.getMicrosecondPosition: " + line.getMicrosecondPosition());
                System.out.println("line.getControls: " + line.getControls());
                System.out.println("line.getFormat: " + line.getFormat());
                System.out.println("line.getLineInfo: " + line.getLineInfo());
                WebradioPlayer.getGui().getAudioDetails().changeChannelsText(getAudioFormatChannels());
                WebradioPlayer.getGui().getAudioDetails().changeFormat(getAudioFormatEncoding());
                WebradioPlayer.getGui().getAudioDetails().changeSamplerate(getAudioFormatSampleRate());

                songLaenge = song.length();
                sampleSizeInBits = audioFormat.getSampleSizeInBits();

                if (in.getFrameLength() == -1) {
                    songDuration = songLaenge / sampleSizeInBits / 1000; // berechnung für mp3
                    bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
                } else {
                    songDuration = in.getFrameLength() / (long) audioFormat.getFrameRate(); // berechnung f�r wav
                    bitRate = (int) (in.available() / (in.getFrameLength() / (long) audioFormat.getFrameRate()) * 8 / 1000);
                }

                in.mark(in.available());
                while ((true) && (!stop)) {
                    isPlaying = true;
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
                        actuallySongTime = line.getMicrosecondPosition() / 1000000 - resetKorrektur;
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
        } while (loopPlay && !stop);

        isPlaying = false;
    }

    @Override
    public void fetchStreamInfo() {
        try {
            resetKorrektur = 0;
            AudioInputStream in = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.getAudioInputStream(url));
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
            line.start();
            songLaenge = song.length();
            sampleSizeInBits = audioFormat.getSampleSizeInBits();

            if (in.getFrameLength() == -1) {
                songDuration = songLaenge / sampleSizeInBits / 1000; // berechnung für mp3
                bitRate = audioFormat.getSampleSizeInBits() * audioFormat.getFrameSize() * audioFormat.getChannels();
            } else {
                songDuration = in.getFrameLength() / (long) audioFormat.getFrameRate(); // berechnung f�r wav
                bitRate = (int) (in.available() / (in.getFrameLength() / (long) audioFormat.getFrameRate()) * 8 / 1000);
            }
            line.close();
            in.close();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("nicht unterst�tztes Format");
        } catch (IOException e) {
            System.out.println("Datei nicht gefunden" + e);
        } catch (LineUnavailableException e) {
            System.out.println("Soundkartenfehler");
        }
        BufferSize = bitRate / 8 * 1000 / 10; // Buffergr��e auf Anzahl der ben�tigten Bytes pro 1/10s
        buffer = new byte[BufferSize];

    }
}
