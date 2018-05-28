package de.dhbw.webradio;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.networkconnection.NetworkConnectivityChecker;
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
        addStations();
        SettingsParser settingsParser = new SettingsParser();
        settings = settingsParser.parsegeneralSettings(new File("C:\\repository\\webradio\\webradio-application\\src\\main\\resources\\settings\\general.yaml"));
        ;
        gui = Gui.getInstance();
        NetworkConnectivityChecker n = NetworkConnectivityChecker.getInstance();
    }

    private static void addStations() {
        try {
            Station s = new Station("FFH", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
            stationList.add(s);
            Station s2 = new Station("SWR 1", new URL("http://mp3-live.swr.de/swr1bw_m.m3u"));
            stationList.add(s2);
            Station s3 = new Station("DasDing", new URL("http://mp3-live.dasding.de/dasding_m.m3u"));
            stationList.add(s3);
            Station s4 = new Station("YouFM", new URL("http://metafiles.gl-systemhaus.de/hr/youfm_2.m3u"));
            stationList.add(s4);
            Station s5 = new Station("bigFM", new URL("http://static.bigfm.de/sites/default/files/playlist/bigFM%20WEBRADIO%20(WinAmp).m3u8"));
            Station s6 = new Station("antenne", new URL("http://stream.antenne.com/hra-nds/mp3-128/IMDADevice/"));
            stationList.add(s5);
            stationList.add(s6);
            Station s7 = new Station("FFH ITunes Top 40", new URL("http://streams.ffh.de/ffhchannels/mp3/hqtop40.m3u"));
            stationList.add(s7);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
