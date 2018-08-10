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
        JMenu stationList = new JMenu("Senderliste");
        menuBar.add(file);
        menuBar.add(settings);
        menuBar.add(stationList);
        JMenuItem exitProgram = new JMenuItem("Beenden");
        exitProgram.addActionListener(e -> System.exit(0));
        file.add(exitProgram);
        JMenuItem generalSettings = new JMenuItem("Allgemeine Einstellungen...");
        generalSettings.addActionListener(new GeneralSettingsEventHandler());
        settings.add(generalSettings);
        JMenuItem addStationItem = new JMenuItem("Sender hinzufügen...");
        addStationItem.addActionListener(e -> new AddStationWindow());
        stationList.add(addStationItem);
        this.add(menuBar);
        //change default center alignment to left alignment
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
