package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.radioplayer.SoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecreaseVolumeListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer player = new SoundPlayer();
        player.decreaseVolume(1);
    }
}
