package de.dhbw.webradio.gui;

import de.dhbw.webradio.eventhandlers.DecreaseVolumeListener;
import de.dhbw.webradio.eventhandlers.IncreaseVolumeListener;
import de.dhbw.webradio.eventhandlers.MuteVolumeListener;
import de.dhbw.webradio.eventhandlers.TogglePlayerListener;

import javax.swing.*;
import java.awt.*;

public class PlayerControlPanel extends JPanel {
    private JButton togglePlayerButton, volumeUpButton, volumeDownButton, muteVolumeButton;

    PlayerControlPanel() {
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new FlowLayout());
        togglePlayerButton = new JButton("Start");
        togglePlayerButton.setEnabled(false);
        togglePlayerButton.addActionListener(new TogglePlayerListener());
        volumeUpButton = new JButton("+");
        volumeUpButton.addActionListener(new IncreaseVolumeListener());
        volumeDownButton = new JButton("-");
        volumeDownButton.addActionListener(new DecreaseVolumeListener());
        muteVolumeButton = new JButton("Lautlos");
        muteVolumeButton.addActionListener(new MuteVolumeListener());
        this.add(togglePlayerButton);
        this.add(volumeUpButton);
        this.add(volumeDownButton);
        this.add(muteVolumeButton);
    }

    public void togglePlayButton() {
        if (this.togglePlayerButton.getText().equalsIgnoreCase("Start")) {
            this.togglePlayerButton.setText("Stop");
        }
    }

    public void toggleMuteVolumeButton() {
        if (this.muteVolumeButton.getText().equalsIgnoreCase("Lautlos")) {
            this.muteVolumeButton.setText("Ton ein");
        } else {
            this.muteVolumeButton.setText("Lautlos");
        }
    }

    public JButton getTogglePlayerButton() {
        return togglePlayerButton;
    }
}
