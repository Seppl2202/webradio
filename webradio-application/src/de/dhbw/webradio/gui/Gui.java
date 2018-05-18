package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.StationsTableModel;
import de.dhbw.webradio.radioplayer.WebradioPlayer;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private PlayerControlPanel playerControlPanel;
    private JPanel mainPanel;
    private StatusBar statusBar;
    private StationsTableModel stationsTableModel;
    private JTable stationsTable;
    private StreamDetails streamDetails;

    public Gui() {
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        playerControlPanel = new PlayerControlPanel();
        mainPanel.add(playerControlPanel, BorderLayout.CENTER);
        statusBar = new StatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        stationsTableModel = new StationsTableModel(WebradioPlayer.getStationList());
        stationsTable = new JTable();
        stationsTable.setModel(stationsTableModel);
        mainPanel.add(new JScrollPane(stationsTable), BorderLayout.WEST);
        streamDetails = new StreamDetails();
        mainPanel.add(streamDetails, BorderLayout.EAST);
    }

    public PlayerControlPanel getPlayerControlPanel() {
        return playerControlPanel;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public StationsTableModel getStationsTableModel() {
        return stationsTableModel;
    }

    public JTable getStationsTable() {
        return stationsTable;
    }

    public StreamDetails getStreamDetails() {
        return streamDetails;
    }

    public void resetComponents() {
        statusBar.updateAdditionalM3uInfo("Keine Informationen verfügbar");
        statusBar.updateActualStation("Aktuell keine Wiedergabe");
        streamDetails.changeSamplerate(0);
        streamDetails.changeChannelsText(0);
        streamDetails.changeFormat("Keine Wiederegabe");
        playerControlPanel.getTogglePlayerButton().setText("Start");
    }
}
