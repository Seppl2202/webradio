package de.dhbw.webradio.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel actualStationLabel;
    private JLabel volumeLabel;
    private JLabel additionalm3uInfo;
    private String seperator = " | ";

    public StatusBar() {
        initialize();
    }

    private void initialize() {
        this.setLayout(new FlowLayout());
        actualStationLabel = new JLabel("Sie hören: aktuell keine Wiedergabe" + seperator);
        volumeLabel = new JLabel("Lautstärke: " + seperator);
        additionalm3uInfo = new JLabel("Aktueller Titel: Keine Informationen verfübgar");
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setPreferredSize(new Dimension(this.getWidth(), 16));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(actualStationLabel);
        this.add(volumeLabel);
        this.add(additionalm3uInfo);
        actualStationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setVisible(true);
    }

    public void updateActualStation(String stationName) {
        actualStationLabel.setText("Sie hören: " + stationName + seperator);
    }

    public void updateVolume(int volume) {
        if (volume == -1) {
            volumeLabel.setText("Lautstärke: lautlos" + seperator);
        } else {
            volumeLabel.setText("Lautstärke: " + volume + seperator);
        }
    }

    public void updateAdditionalM3uInfo(String info) {
        additionalm3uInfo.setText("Aktueller Titel: " + info);
    }
}
