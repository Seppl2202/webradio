package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;

public class GUIHandler {
    private static GUIHandler guiHandler;

    private GUIHandler() {

    }

    public static GUIHandler getInstance() {
        if (guiHandler == null) {
            return new GUIHandler();
        }
        return guiHandler;
    }

    public void notifyNewIcyData(IcyInputStreamReader reader) {
        WebradioPlayer.getGui().getStatusBar().updateActualStation(reader.getStationName());
        WebradioPlayer.getGui().getStatusBar().updateAdditionalM3uInfo(reader.getActualTitle());
        System.err.println("new data arrvived: "  + reader.getActualTitle());
    }

    public void updateGui(Station station, IcyInputStreamReader icyReader) {
        WebradioPlayer.getGui().getStreamDetails().updateM3uUrl("Aktuell wird kein M3U-Stream wiedergegben");
        WebradioPlayer.getGui().getStreamDetails().updateM3uInfo("Kein M3U-Stream verfügbar");
        WebradioPlayer.getGui().getStreamDetails().updateStreamUrl(station.getStationURL().toString());
        WebradioPlayer.getGui().getStreamDetails().updateStationName(station.getName());
        WebradioPlayer.getGui().getStatusBar().updateActualStation(icyReader.getStationName());
        WebradioPlayer.getGui().getStatusBar().updateAdditionalM3uInfo(icyReader.getActualTitle());
    }

    public void updatePlayerDetails(AbstractPlayer player) {
        WebradioPlayer.getGui().getPlayerControlPanel().togglePlayButton();
        WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
    }

    public void mutePlayer() {
        WebradioPlayer.getGui().getStatusBar().updateVolume(-1);
    }

    public void updatePlayerVolume(AbstractPlayer player) {
        WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
    }
}
