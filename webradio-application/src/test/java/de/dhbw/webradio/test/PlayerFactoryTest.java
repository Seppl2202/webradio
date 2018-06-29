package de.dhbw.webradio.test;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.m3uparser.M3uParser;
import de.dhbw.webradio.models.M3UInfo;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AACPlayer;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.Mp3Player;
import de.dhbw.webradio.radioplayer.PlayerFactory;
import net.sourceforge.jaad.Play;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class PlayerFactoryTest {


    @Test
    public void get() throws MalformedURLException {
        Station s1 = new Station("test1", new URL("http://streams.bigfm.de/bigfm-charts-128-aac?usid=0-0-H-A-D-30"));
       Station s2 = new Station("test2", new URL("http://hr-youfm-live.cast.addradio.de/hr/youfm/live/mp3/128/stream.mp3"));
        PlayerFactory p = new PlayerFactory();
        AbstractPlayer player = p.get(s1);
        AbstractPlayer player2 = p.get(s2);
        assertEquals(true, player instanceof AACPlayer);
        assertEquals(true, player2 instanceof Mp3Player);
    }

    @Test
    public void getUserSelection() throws IOException, NoURLTagFoundException, UnsupportedAudioFileException {
        Station s = new Station("test1", new URL("http://static.bigfm.de/sites/default/files/playlist/bigFM%20WEBRADIO%20(WinAmp).m3u8"));
        s.isURLValid();
        PlayerFactory p = new PlayerFactory();
        M3uParser parser = new M3uParser();
        String content = parser.parseFileFromUrlToString(s.getStationURL());
        M3UInfo info = p.getUserSelection(content, parser);
        assertEquals("bigFM DEUTSCHLAND", info.getTitleInfo());
    }
}