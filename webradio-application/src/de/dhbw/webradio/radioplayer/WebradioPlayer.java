package de.dhbw.webradio.radioplayer;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;

import java.net.MalformedURLException;
import java.net.URL;

public class WebradioPlayer {
    private static Gui gui;
    private static SoundPlayer player;
    public static void main(String[] args) {
        gui = new Gui();
        player = new SoundPlayer();
//        SoundPlayer player = new SoundPlayer();
//        try {
//            Station station = new Station("FFH", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
//            if(!station.isURLValid()) {
//                throw new MalformedURLException("URL: " + station.getStationURL() + "did not returned status 200");
//            }
//            player.setUrl(station.getStationURL());
//            player.setVolume(80);
//            player.play();
//        } catch (MalformedURLException mue) {
//            mue.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static Gui getGui() {
        return gui;
    }

    public static SoundPlayer getPlayer() {
        return player;
    }
}
