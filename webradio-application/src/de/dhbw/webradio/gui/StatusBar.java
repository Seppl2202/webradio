package de.dhbw.webradio.gui;

import de.dhbw.webradio.radioplayer.WebradioPlayer;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel actualStationLabel;
    private JLabel volumeLabel;

    public StatusBar() {
        initialize();
    }

    private void initialize() {
        this.setLayout(new FlowLayout());
        actualStationLabel = new JLabel("Sie hören: aktuell keine Wiedergabe");
        volumeLabel = new JLabel("Lautstärke: ");
        this.add(actualStationLabel);
        this.add(volumeLabel);
        this.setVisible(true);
    }

    public void updateActualStation(String stationName) {
        actualStationLabel.setText("Sie hören: " + stationName);
    }

    public void updateVolume(int volume) {
        if (volume == -1) {
            volumeLabel.setText("Lautstärke: lautlos");
        } else {
            volumeLabel.setText("Lautstärke: " + volume);
        }
    }
}
