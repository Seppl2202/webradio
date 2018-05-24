package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.models.StationsTableModel;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public static Gui gui = new Gui();
    private PlayerControlPanel playerControlPanel;
    private JPanel mainPanel;
    private StatusBar statusBar;
    private StationsTableModel stationsTableModel;
    private JTable stationsTable;
    private StreamDetails streamDetails;
    private MenuBar menuBar;
    private JTabbedPane streamAudioDetails;
    private AudioDetails audioDetails;

    private Gui() {
        initialize();
    }

    public static Gui getInstance() {
        return gui;
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        playerControlPanel = new PlayerControlPanel();
        statusBar = new StatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        stationsTableModel = new StationsTableModel(WebradioPlayer.getStationList());
        stationsTable = new JTable();
        stationsTable.setModel(stationsTableModel);
        mainPanel.add(new JScrollPane(stationsTable), BorderLayout.WEST);
        streamDetails = new StreamDetails();
        menuBar = new MenuBar();
        mainPanel.add(menuBar, BorderLayout.NORTH);
        audioDetails = new AudioDetails();
        streamAudioDetails = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        streamAudioDetails.add("Player-Steuerung", playerControlPanel);
        streamAudioDetails.add("Stream-Details", streamDetails);
        streamAudioDetails.add("Audio-Details", audioDetails);
        mainPanel.add(streamAudioDetails, BorderLayout.CENTER);
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

    }

    public AudioDetails getAudioDetails() {
        return audioDetails;
    }
}
