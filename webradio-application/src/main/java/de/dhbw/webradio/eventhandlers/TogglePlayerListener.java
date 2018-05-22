package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.PlayerFactory;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class TogglePlayerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        PlayerFactory playerFactory = new PlayerFactory();
        Station s = WebradioPlayer.getGui().getStationsTableModel().getStationFromIndex(WebradioPlayer.getGui().getStationsTable().getSelectedRow());
        AbstractPlayer actualPlayer = WebradioPlayer.getPlayer();
        //if no player was created yet, directly create a new one
        if (actualPlayer == null) {
            createPlayer(playerFactory, s);
        }
        if (actualPlayer.isPlaying()) {
            actualPlayer.stop();
        } else {
            if (!(s == null)) {
                try {
                    if (!s.isURLValid()) {
                        throw new MalformedURLException("URL: " + s.getStationURL() + "did not returned status 200");
                    }
                    createPlayer(playerFactory, s);
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void createPlayer(PlayerFactory playerFactory, Station s) {
        AbstractPlayer player = playerFactory.get(s);
        if (player == null) {
            throw new IllegalArgumentException("Station " + s + "did not contain a valid file extension");
        }
        WebradioPlayer.setPlayer(player);
        player.play();
        WebradioPlayer.getGui().getPlayerControlPanel().togglePlayButton();
        WebradioPlayer.getGui().getStatusBar().updateActualStation(s.getName());
        WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
        updateGui(s);
    }

    private void updateGui(Station station) {
        WebradioPlayer.getGui().getStreamDetails().updateM3uUrl("Aktuell wird kein M3U-Stream wiedergegben");
        WebradioPlayer.getGui().getStreamDetails().updateM3uInfo("Kein M3U-Stream verfügbar");
        WebradioPlayer.getGui().getStreamDetails().updateStreamUrl(station.getStationURL().toString());
        WebradioPlayer.getGui().getStreamDetails().updateStationName(station.getName());
    }
}
