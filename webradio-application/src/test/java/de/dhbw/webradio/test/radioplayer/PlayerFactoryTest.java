package de.dhbw.webradio.test.radioplayer;

import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.playlistparser.M3uParser;
import de.dhbw.webradio.models.InformationObject;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AACPlayer;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.MP3Player;
import de.dhbw.webradio.radioplayer.PlayerFactory;
import org.junit.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class PlayerFactoryTest {


    @Test
    public void get() throws MalformedURLException {
        Station s1 = new Station("test1", new URL("http://streams.bigfm.de/bigfm-charts-128-aac?usid=0-0-H-A-D-30"));
        Station s2 = new Station("test2", new URL("http://hr-youfm-live.cast.addradio.de/hr/youfm/live/mp3/128/stream.mp3"));
        Station s3 = new Station("test3", new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
        PlayerFactory p = new PlayerFactory();
        //aac
        AbstractPlayer player = p.get(s1);
        //mp3
        AbstractPlayer player2 = p.get(s2);
        //single mp3 stream from m3u
        AbstractPlayer player3 = p.get(s3);
        assertEquals(true, player instanceof AACPlayer);
        assertEquals(true, player2 instanceof MP3Player);
        assertEquals(true, player3 instanceof MP3Player);
    }

    /**
     * please select the first entry of the selection dialog
     * @throws IOException
     * @throws NoURLTagFoundException
     * @throws UnsupportedAudioFileException
     */
    @Test
    public void getUserSelection() throws IOException, NoURLTagFoundException, UnsupportedAudioFileException {
        Station s = new Station("test1", new URL("http://static.bigfm.de/sites/default/files/playlist/bigFM%20WEBRADIO%20(WinAmp).m3u8"));
        s.isURLValid();
        PlayerFactory p = new PlayerFactory();
        M3uParser parser = new M3uParser();
        String content = parser.parseFileFromUrlToString(s.getStationURL());
        InformationObject info = p.getUserSelection(content, parser);
        assertEquals("bigFM DEUTSCHLAND", info.getTitleInfo());
    }
    @Test
    public void getPLSSelection() throws MalformedURLException {
        //test with list size = 1
        //test with list size > 1
    }
}
