package de.dhbw.webradio;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.settings.Settings;
import de.dhbw.webradio.settings.SettingsParser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebradioPlayer {
    private static Gui gui;
    private static AbstractPlayer player;
    private static List<Station> stationList;
    private static Settings settings;

    public static void main(String[] args) {
        stationList = new ArrayList();
        try {
            Station s = new Station("FFH", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
            stationList.add(s);
            Station s2 = new Station("SWR 1", new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
            stationList.add(s2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SettingsParser settingsParser = new SettingsParser();
        settings = settingsParser.parsegeneralSettings(new File("C:\\repository\\webradio\\webradio-application\\src\\main\\resources\\settings\\general.yaml"));
        ;
        gui = new Gui();
    }

    public static Gui getGui() {
        return gui;
    }

    public static AbstractPlayer getPlayer() {
        return player;
    }

    public static void setPlayer(AbstractPlayer newPlayer) {
        player = newPlayer;
    }

    public static List<Station> getStationList() {
        return stationList;
    }

    public static boolean addStation(Station s) {
        return stationList.add(s);
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings settings) {
        WebradioPlayer.settings = settings;
    }
}
