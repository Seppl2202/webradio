package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.models.StationsTableModel;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class TogglePlayerListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Station s = WebradioPlayer.getGui().getStationsTableModel().getStationFromIndex(WebradioPlayer.getGui().getStationsTable().getSelectedRow());
        PlayerSelectionController psc = new PlayerSelectionController(s);
        AbstractPlayer player = psc.getPlayerForFileType();
        if (player.isPlaying()) {
            player.stop();
        } else {
            if (!(s == null)) {
                try {
                    if (!s.isURLValid()) {
                        throw new MalformedURLException("URL: " + s.getStationURL() + "did not returned status 200");
                    }
                    player.play();
                    WebradioPlayer.getGui().getPlayerControlPanel().togglePlayButton();
                    WebradioPlayer.getGui().getStatusBar().updateActualStation(s.getName());
                    WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
