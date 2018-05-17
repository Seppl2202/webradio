package de.dhbw.webradio.gui;

import de.dhbw.webradio.eventhandlers.TogglePlayerListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerControlPanel extends JPanel {
    private JButton togglePlayerButton, volumeUpButton, volumeDownButton;
    PlayerControlPanel(){
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new FlowLayout());
        togglePlayerButton = new JButton("Start");
        togglePlayerButton.addActionListener(new TogglePlayerListener());
        volumeUpButton = new JButton("+");
        volumeDownButton = new JButton("-");
        this.add(togglePlayerButton);
        this.add(volumeUpButton);
        this.add(volumeDownButton);
    }

    public void togglePlayButton() {
        if(this.togglePlayerButton.getText().equalsIgnoreCase("Start")) {
            this.togglePlayerButton.setText("Stop");
        } else {
            this.togglePlayerButton.setText("Start");
        }
    }
}
