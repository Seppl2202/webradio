package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.StationsTableModel;
import de.dhbw.webradio.WebradioPlayer;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private PlayerControlPanel playerControlPanel;
    private JPanel mainPanel;
    private StatusBar statusBar;
    private StationsTableModel stationsTableModel;
    private JTable stationsTable;
    private StreamDetails streamDetails;
    private MenuBar menuBar;
    private JTabbedPane streamAudioDetails;
    private AudioDetails audioDetails;

    public Gui() {
        initialize();
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
        statusBar.updateAdditionalM3uInfo("Keine Informationen verfügbar");
        statusBar.updateActualStation("Aktuell keine Wiedergabe");
        audioDetails.changeSamplerate(0);
        audioDetails.changeChannelsText(0);
        audioDetails.changeFormat("Aktuell keine Wiederegabe");
        playerControlPanel.getTogglePlayerButton().setText("Start");
        streamDetails.updateM3uInfo("Aktuell keine Wiedergabe");
        streamDetails.updateStationName("Aktuell keine Wiedergabe");
        streamDetails.updateM3uUrl("Aktuell keine Wiedergabe");
        streamDetails.updateStreamUrl("Aktuell keine Wiedergabe");
    }

    public AudioDetails getAudioDetails() {
        return audioDetails;
    }
}
