package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.radioplayer.SoundPlayer;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseVolumeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer player = WebradioPlayer.getPlayer();
        player.increaseVolume(1);
    }
}
