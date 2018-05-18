package de.dhbw.webradio.radioplayer;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.m3uparser.M3uParser;
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
            Station s2 = new Station("SWR 1", new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
            stationList.add(s2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        player = new Mp3Player();
        gui = new Gui();
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
