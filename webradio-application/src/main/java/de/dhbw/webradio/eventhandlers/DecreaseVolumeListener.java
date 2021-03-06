package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.gui.GUIHandler;
import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecreaseVolumeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        AbstractPlayer player = WebradioPlayer.getPlayer();
        player.decreaseVolume(1);
        GUIHandler.getInstance().updatePlayerVolume(player);
    }
}
