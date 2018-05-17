package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.models.StationListModel;
import de.dhbw.webradio.radioplayer.SoundPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class TogglePlayerListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Station s = StationListModel.getSelectedStation();
        SoundPlayer player = WebradioPlayer.getPlayer();
        if (player.isPlaying()) {
            player.stop();
            WebradioPlayer.getGui().getPlayerControlPanel().togglePlayButton();
        } else {
            if (!(s == null)) {
                try {
                    if (!s.isURLValid()) {
                        throw new MalformedURLException("URL: " + s.getStationURL() + "did not returned status 200");
                    }
                    player.setUrl(s.getStationURL());
                    player.setVolume(80);
                    player.play();
                    WebradioPlayer.getGui().getPlayerControlPanel().togglePlayButton();
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
