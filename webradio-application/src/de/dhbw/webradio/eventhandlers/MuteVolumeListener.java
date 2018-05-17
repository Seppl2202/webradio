package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.radioplayer.SoundPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuteVolumeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer player = WebradioPlayer.getPlayer();
        if (!(player.isMute())) {
            player.setMute(true);
        } else {
            player.setMute(false);
        }
        WebradioPlayer.getGui().getPlayerControlPanel().toggleMuteVolumeButton();
    }
}
