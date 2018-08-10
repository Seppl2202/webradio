package de.dhbw.webradio.radioplayer;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.net.URL;
import java.util.Map;

public abstract class AbstractPlayer {

    protected int gainPercent = 90;  //gibt die Lautstärke in Prozent an.  (0% = -80dB und 100% = 6dB)
    protected Boolean stop = false;
    protected URL url = null;
    protected int sampleSizeInBits = 0;
    protected long songLaenge = 0;
    protected boolean reset = false;
    protected long resetKorrektur = 0;
    protected boolean pause = false;
    protected boolean mute = false;
    protected int volume = gainPercent;
    protected int bitRate = 0;
    protected int audioFormatChannels;
    protected float audioFormatFrameRate;
    protected int audioFormatFrameSize;
    protected float audioFormatSampleRate;
    protected int audioFormatSampleSizeInBits;
    protected String audioFormatEncoding;
    protected Map<String, Object> audioFormatproperties;
    protected AudioInputStream ais = null;
    protected IcyInputStreamReader icyReader;


    public void stop() {
        stop = true;
    }

    public int getVolume() {
        return this.gainPercent;
    }

    public void setVolume(int volume) {
        if (volume <= 100 && volume >= 0) {
            this.gainPercent = volume;
        }
    }

    public abstract void play();

    public boolean isPlaying() {
        return !stop;
    }

    public void reset(boolean reset) {
        this.reset = reset;
    }


    public boolean isPaused() {
        return this.pause;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setMute(boolean mute) {
        if ((mute) && (!this.mute)) {
            volume = this.getVolume();
            this.setVolume(0);
        } else {
            this.setVolume(volume);
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


    public void increaseVolume(int step) {
        setVolume(getVolume() + step);
    }


    public void decreaseVolume(int step) {
        setVolume(getVolume() - step);
    }

    public int getBitRate() {
        return bitRate;
    }

    public IcyInputStreamReader getIcyReader() {
        return icyReader;
    }

    public void setIcyReader(IcyInputStreamReader icyReader) {
        this.icyReader = icyReader;
    }
}
