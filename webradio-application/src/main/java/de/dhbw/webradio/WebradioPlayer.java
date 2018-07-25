package de.dhbw.webradio;


import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.h2database.DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseConnector;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.networkconnection.NetworkConnectivityChecker;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.recording.RecorderController;
import de.dhbw.webradio.settings.Settings;
import de.dhbw.webradio.settings.SettingsParser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WebradioPlayer {

    public static File settingsDirectory = new File("C:\\repository\\webradio\\webradio-application\\src\\main\\resources\\settings\\general.yaml").getAbsoluteFile();
    private static Gui gui;
    private static AbstractPlayer player;
    private static List<Station> stationList = new ArrayList<>();
    private static Settings settings;
    private static DatabaseConnector databaseConnector = H2DatabaseConnector.getInstance();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        InitializeH2Database.initialiteDatabase();
        SettingsParser settingsParser = new SettingsParser();
        parseSettings(settingsParser);
        gui = Gui.getInstance();
        NetworkConnectivityChecker n = NetworkConnectivityChecker.getInstance();
        RecorderController.getInstance();
    }

    public static void parseSettings(SettingsParser settingsParser) {
        settings = settingsParser.parsegeneralSettings(settingsDirectory);
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
            Station s7 = new Station("FFH AAC", new URL("http://mp3.ffh.de/radioffh/hqlivestream.aac"));
            stationList.add(s7);
            addStation(s);
            addStation(s2);
            addStation(s3);
            addStation(s4);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void deleteStation(Station s) {
        databaseConnector.deleteStation(s);
        stationList.remove(s);
        Gui.getInstance().getStationsTableModel().removeStation(s);
        Gui.getInstance().getStationsTableModel().fireTableDataChanged();
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
        return databaseConnector.getStations();

    }

    public synchronized static boolean addStation(Station s) {
        stationList.add(s);
        Gui.getInstance().getStationsTableModel().addRow(s);
        Gui.getInstance().getStationsTableModel().fireTableDataChanged();
        return databaseConnector.addStation(s);
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings settings) {
        WebradioPlayer.settings = settings;
    }

    public static synchronized boolean addScheduledRecord(ScheduledRecord record) {
        RecorderController.getInstance().addScheduledRecord(record);
        databaseConnector.addScheduledRecord(record);
        return true;
    }

    public static synchronized boolean deleteScheduledRecord(ScheduledRecord toDelete) {
        RecorderController.getInstance().removeScheduledRecord(toDelete);
        databaseConnector.deleteScheduledRecord(toDelete);
        return true;
    }

}
