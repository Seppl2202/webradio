package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.radioplayer.AbstractPlayer;
import de.dhbw.webradio.radioplayer.SoundPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseVolumeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AbstractPlayer player = WebradioPlayer.getPlayer();
        player.increaseVolume(1);
        WebradioPlayer.getGui().getStatusBar().updateVolume(player.getVolume());
    }
}
