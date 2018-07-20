package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.IcyInputStreamReader;
import de.dhbw.webradio.radioplayer.MetainformationReader;

import java.util.Optional;

public class GUIHandler implements Handler {
    private static Handler guiHandler = new GUIHandler();

    private GUIHandler() {
    }

    public static Handler getInstance() {
        return guiHandler;
    }

    @Override
    public void notifyNewIcyData(MetainformationReader reader) {
        Gui.getInstance().getStatusBar().updateActualStation(reader.getStationName());
        Gui.getInstance().getStatusBar().updateAdditionalM3uInfo(reader.getActualTitle());
        Gui.getInstance().getStreamDetails().updateM3uUrl(reader.getStationName() + ": " + reader.getStationUrl());
        Gui.getInstance().getStreamDetails().updateM3uInfo(reader.getActualTitle());
        Gui.getInstance().getStreamDetails().updateRealTitelInfo(reader.getActualMusicTitle());
    }

    @Override
    public void updateGui(Station station, MetainformationReader icyReader, AbstractPlayer player) {
        try {
            //two seconds delay to let icyReader fetch all information
            Thread.sleep(2000);
            Optional<String> titleInfo = Optional.ofNullable(icyReader.getActualTitle());
            Optional<String> stationInfo = Optional.ofNullable(icyReader.getStationName() + ": " + icyReader.getStationUrl());
            Gui.getInstance().getStreamDetails().updateM3uUrl(stationInfo.orElse("Keine Informationen verfügbar"));
            Gui.getInstance().getStreamDetails().updateM3uInfo(titleInfo.orElse("Keine Informationen verfügbar"));
            Gui.getInstance().getStreamDetails().updateStreamUrl(player.getUrl().toString());
            Gui.getInstance().getStreamDetails().updateStationName(station.getName());
            Gui.getInstance().getStreamDetails().updateRealTitelInfo(icyReader.getActualMusicTitle());
            Gui.getInstance().getStatusBar().updateActualStation(stationInfo.orElse("Keine Informationen verfügbar"));
            Gui.getInstance().getStatusBar().updateAdditionalM3uInfo(titleInfo.orElse("Keine Informationen verfügbar"));
            Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlayerDetails(AbstractPlayer player) {
        Gui.getInstance().getPlayerControlPanel().togglePlayButton();
        Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
    }

    @Override
    public void mutePlayer() {
        Gui.getInstance().getStatusBar().updateVolume(-1);
    }

    @Override
    public void updatePlayerVolume(AbstractPlayer player) {
        Gui.getInstance().getStatusBar().updateVolume(player.getVolume());
    }

    @Override
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
        Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setEnabled(true);
        Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setText("Start");
    }

    @Override
    public void togglePlayButton() {
        if (Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().getText().equalsIgnoreCase("start")) {
            Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setText("Stop");
        } else {
            Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setText("Start");
        }
    }

    @Override
    public void updateAudioDetails(AbstractPlayer player) {
        Gui.getInstance().getAudioDetails().changeChannelsText(player.getAudioFormatChannels());
        Gui.getInstance().getAudioDetails().changeFormat(player.getAudioFormatEncoding());
        Gui.getInstance().getAudioDetails().changeSamplerate(player.getAudioFormatSampleRate());
    }

    @Override
    public void toggleControls(boolean statusToSet) {
        Gui.getInstance().getPlayerControlPanel().getTogglePlayerButton().setEnabled(statusToSet);
        Gui.getInstance().getRecorderTab().toggleControls(statusToSet);
    }
}
