package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuteVolumeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        AbstractPlayer player = WebradioPlayer.getPlayer();
        if (!(player.isMute())) {
            player.setMute(true);
            GUIHandler.getInstance().mutePlayer();
        } else {
            player.setMute(false);
            GUIHandler.getInstance().updatePlayerVolume(player);
        }
        Gui.getInstance().getPlayerControlPanel().toggleMuteVolumeButton();
    }
}
