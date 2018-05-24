package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;

public class GUIHandler {
    private static GUIHandler guiHandler = new GUIHandler();

    private GUIHandler() {

    }

    public static GUIHandler getInstance() {
        return guiHandler;
    }

    public void notifyNewIcyData(IcyInputStreamReader reader) {
        Gui.getInstance().getStatusBar().updateActualStation(reader.getStationName());
        Gui.getInstance().getStatusBar().updateAdditionalM3uInfo(reader.getActualTitle());
        Gui.getInstance().getStreamDetails().updateM3uUrl(reader.getStationName() + ": " + reader.getStationUrl());
        Gui.getInstance().getStreamDetails().updateM3uInfo(reader.getActualTitle());
    }

    public void updateGui(Station station, IcyInputStreamReader icyReader, AbstractPlayer player) {
        Gui.getInstance().getStreamDetails().updateM3uUrl(icyReader.getStationName() + ": " + icyReader.getStationUrl());
        Gui.getInstance().getStreamDetails().updateM3uInfo(icyReader.getActualTitle());
        Gui.getInstance().getStreamDetails().updateStreamUrl(station.getStationURL().toString());
        Gui.getInstance().getStreamDetails().updateStationName(station.getName());
        Gui.getInstance().getStatusBar().updateActualStation(icyReader.getStationName());
        Gui.getInstance().getStatusBar().updateAdditionalM3uInfo(icyReader.getActualTitle());
        Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
    }

    public void updatePlayerDetails(AbstractPlayer player) {
        Gui.getInstance().getPlayerControlPanel().togglePlayButton();
        Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
    }

    public void mutePlayer() {
        Gui.getInstance().getStatusBar().updateVolume(-1);
    }

    public void updatePlayerVolume(AbstractPlayer player) {
        Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
    }

    public void resetComponents() {
        Gui.getInstance().getStatusBar().updateAdditionalM3uInfo("Keine Informationen verfügbar");
        Gui.getInstance().getStatusBar().updateActualStation("Aktuell keine Wiedergabe");
        Gui.getInstance().getAudioDetails().changeSamplerate(0);
        Gui.getInstance().getAudioDetails().changeChannelsText(0);
        Gui.getInstance().getAudioDetails().changeFormat("Aktuell keine Wiederegabe");
        Gui.getInstance().getStreamDetails().updateM3uInfo("Aktuell keine Wiedergabe");
        Gui.getInstance().getStreamDetails().updateStationName("Aktuell keine Wiedergabe");
        Gui.getInstance().getStreamDetails().updateM3uUrl("Aktuell keine Wiedergabe");
        Gui.getInstance().getStreamDetails().updateStreamUrl("Aktuell keine Wiedergabe");
        togglePlayButton();
    }

    public void togglePlayButton() {
        if (Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().getText().equalsIgnoreCase("start")) {
            Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setText("Stop");
        } else {
            Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setText("Start");
        }
    }

    public void updateAudioDetails(AbstractPlayer player) {
        Gui.getInstance().getAudioDetails().changeChannelsText(player.getAudioFormatChannels());
        Gui.getInstance().getAudioDetails().changeFormat(player.getAudioFormatEncoding());
        Gui.getInstance().getAudioDetails().changeSamplerate(player.getAudioFormatSampleRate());
    }
}
