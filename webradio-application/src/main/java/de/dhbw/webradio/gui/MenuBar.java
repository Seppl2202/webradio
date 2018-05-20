package de.dhbw.webradio.gui;

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
        JMenu recording = new JMenu("Aufnahme");
        menuBar.add(file);
        menuBar.add(settings);
        menuBar.add(recording);
        JMenuItem exitProgram = new JMenuItem("Beenden");
        file.add(exitProgram);
        JMenuItem generalSettings = new JMenuItem("Allgemeine Einstellungen");
        JMenuItem generalRecordingSettings = new JMenuItem("Einstellungen Audioaufnahme");
        JMenuItem fileSystemRecodingSystems = new JMenuItem("Verzeichniseinstellungen Audioaufnahme");
        settings.add(generalSettings);
        recording.add(generalRecordingSettings);
        recording.add(fileSystemRecodingSystems);
        this.add(menuBar);
        //change default center alignment to left alignment
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
