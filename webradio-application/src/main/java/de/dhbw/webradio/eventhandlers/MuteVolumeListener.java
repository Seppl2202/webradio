package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuteVolumeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractPlayer player = WebradioPlayer.getPlayer();
        if (!(player.isMute())) {
            player.setMute(true);
            WebradioPlayer.getGui().getStatusBar().updateVolume(-1);
        } else {
            player.setMute(false);
            WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
        }
        WebradioPlayer.getGui().getPlayerControlPanel().toggleMuteVolumeButton();
    }
}
