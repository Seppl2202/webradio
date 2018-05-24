package de.dhbw.webradio.gui;

import de.dhbw.webradio.eventhandlers.GeneralSettingsEventHandler;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JPanel {
    private JMenuBar menuBar;

    public MenuBar() {
        initializePanel();
    }

    private void initializePanel() {
        menuBar = new JMenuBar();
        JMenu file = new JMenu("Datei");
        JMenu settings = new JMenu("Einstellungen");
        menuBar.add(file);
        menuBar.add(settings);
        JMenuItem exitProgram = new JMenuItem("Beenden");
        file.add(exitProgram);
        JMenuItem generalSettings = new JMenuItem("Allgemeine Einstellungen");
        generalSettings.addActionListener(new GeneralSettingsEventHandler());
        JMenuItem generalRecordingSettings = new JMenuItem("Einstellungen Audioaufnahme");
        settings.add(generalSettings);
        settings.add(generalRecordingSettings);
        this.add(menuBar);
        //change default center alignment to left alignment
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
