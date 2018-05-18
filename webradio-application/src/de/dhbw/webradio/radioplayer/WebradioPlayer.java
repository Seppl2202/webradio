package de.dhbw.webradio.radioplayer;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebradioPlayer {
    private static Gui gui;
    private static AbstractPlayer player;
    private static List<Station> stationList;

    public static void main(String[] args) {
        stationList = new ArrayList<>();
        try {
            Station s = new Station("FFH", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
            stationList.add(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        player = new Mp3Player();
        gui = new Gui();
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

    public static AbstractPlayer getPlayer() {
        return player;
    }

    public static List<Station> getStationList() { return stationList; }

    public static boolean addStation(Station s) { return stationList.add(s); }
}
