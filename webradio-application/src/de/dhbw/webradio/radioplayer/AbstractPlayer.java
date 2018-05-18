package de.dhbw.webradio.radioplayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public abstract class AbstractPlayer {
    private static int BufferSize = 1024; // Anzahl der Daten, die aufeinmal an die Soundkarte geschickt werden.
    private static byte[] buffer = new byte[BufferSize];
    private int gainPercent = 90;  //gibt die Lautstärke in Prozent an.  (0% = -80dB und 100% = 6dB)
    private Boolean stop = false;
    private Boolean loopPlay = false;
    private File song = new File("");
    private URL url = null;
    private long songDuration = 0;
    private long actuallySongTime = 0;
    private int sampleSizeInBits = 0;
    private long songLaenge = 0;
    private boolean reset = false;
    private Boolean isPlaying = false;
    private long resetKorrektur = 0;
    private boolean pause = false;
    private boolean mute = false;
    private int lautstaerke = gainPercent;
    private int bitRate = 0;
    private int audioFormatChannels;
    private float audioFormatFrameRate;
    private int audioFormatFrameSize;
    private float audioFormatSampleRate;
    private int audioFormatSampleSizeInBits;
    private String audioFormatEncoding;
    private Map<String, Object> audioFormatproperties;
    private AudioInputStream ais = null;

    public long getActuallySongTime() {
        return this.actuallySongTime;
    }

    public boolean isLoopPlay() {
        return this.loopPlay;
    }

    public void setLoopPlay(Boolean loop) {
        this.loopPlay = loop;
    }

    public void stop() {
        stop = true;
        actuallySongTime = 0;
    }

    public int getVolume() {
        return this.gainPercent;
    }

    public void setVolume(int volume) {
        if (volume <= 100 && volume >= 0) {
            this.gainPercent = volume;
        }
    }

    public abstract void setInputStream();


    public abstract void fetchStreamInfo();

    public abstract void play();

    public File getSong() {
        return this.song;
    }

    public void setSong(String song) throws MalformedURLException {
        this.song = new File(song);
        this.url = new URL("file:/" + this.song);
        setInputStream();
        fetchStreamInfo();
    }

    public long getSongDuration() {
        return this.songDuration;
    }

    public void reset(boolean reset) {
        this.reset = reset;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public boolean isPaused() {
        return this.pause;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setMute(boolean mute) {
        if ((mute) && (!this.mute)) {
            lautstaerke = this.getVolume();
            this.setVolume(0);
        } else {
            this.setVolume(lautstaerke);
        }
        this.mute = mute;
    }

    public URL getUrl() {
        return url;
    }

    public abstract void setUrl(URL url);

    public int getAudioFormatChannels() {
        return audioFormatChannels;
    }

    public float getAudioFormatFrameRate() {
        return audioFormatFrameRate;
    }


    public int getAudioFormatFrameSize() {
        return audioFormatFrameSize;
    }

    public float getAudioFormatSampleRate() {
        return audioFormatSampleRate;
    }

    public int getAudioFormatSampleSizeInBits() {
        return audioFormatSampleSizeInBits;
    }


    public String getAudioFormatEncoding() {
        return audioFormatEncoding;
    }

    public Map<String, Object> getAudioFormatproperties() {
        return audioFormatproperties;
    }

    public boolean isNetConnection() {
        if (song.length() == 0 && !(url == null)) {
            return true;
        } else {
            return false;
        }
    }

    public void increaseVolume(int step) {
        setVolume(getVolume() + step);
    }


    public void decreaseVolume(int step) {
        setVolume(getVolume() - step);
    }

    public int getBitRate() {
        return bitRate;
    }
}
