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

    public int gainPercent = 90;  //gibt die Lautstärke in Prozent an.  (0% = -80dB und 100% = 6dB)
    public Boolean stop = false;
    public Boolean loopPlay = false;
    public File song = new File("");
    public URL url = null;
    public long songDuration = 0;
    public long actuallySongTime = 0;
    public int sampleSizeInBits = 0;
    public long songLaenge = 0;
    public boolean reset = false;
    public Boolean isPlaying = false;
    public long resetKorrektur = 0;
    public boolean pause = false;
    public boolean mute = false;
    public int lautstaerke = gainPercent;
    public int bitRate = 0;
    public int audioFormatChannels;
    public float audioFormatFrameRate;
    public int audioFormatFrameSize;
    public float audioFormatSampleRate;
    public int audioFormatSampleSizeInBits;
    public String audioFormatEncoding;
    public Map<String, Object> audioFormatproperties;
    public AudioInputStream ais = null;

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
