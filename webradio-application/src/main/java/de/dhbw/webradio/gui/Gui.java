package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.StationsTableModel;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private static Gui gui = new Gui();
    private PlayerControlPanel playerControlPanel;
    private JPanel mainPanel;
    private StatusBar statusBar;
    private StationsTable stationsTable;
    private StreamDetails streamDetails;
    private MenuBar menuBar;
    private JTabbedPane streamAudioDetails;
    private AudioDetails audioDetails;
    private RecorderTab recorderTab;

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
        stationsTable = new StationsTable();
        streamDetails = new StreamDetails();
        menuBar = new MenuBar();
        mainPanel.add(menuBar, BorderLayout.NORTH);
        audioDetails = new AudioDetails();
        streamAudioDetails = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        streamAudioDetails.add("Player-Steuerung", playerControlPanel);
        streamAudioDetails.add("Stream-Details", streamDetails);
        streamAudioDetails.add("Audio-Details", audioDetails);
        recorderTab = new RecorderTab();
        streamAudioDetails.add("Aufnahme", recorderTab);
        JPanel splitListAndControlsPanel = new JPanel(new GridLayout(1, 2));
        splitListAndControlsPanel.add(new JScrollPane(stationsTable), BorderLayout.CENTER);
        splitListAndControlsPanel.add(streamAudioDetails);
        mainPanel.add(splitListAndControlsPanel, BorderLayout.CENTER);

    }

    public PlayerControlPanel getPlayerControlPanel() {
        return playerControlPanel;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public StationsTableModel getStationsTableModel() {
        return stationsTable.getTableModel();
    }

    public JTable getStationsTable() {
        return stationsTable.getStationsTable();
    }

    public StreamDetails getStreamDetails() {
        return streamDetails;
    }

    public void resetComponents() {

    }

    public AudioDetails getAudioDetails() {
        return audioDetails;
    }

    public RecorderTab getRecorderTab() {
        return this.recorderTab;
    }
}
