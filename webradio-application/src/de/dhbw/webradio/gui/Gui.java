package de.dhbw.webradio.gui;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    private PlayerControlPanel playerControlPanel;
    private JPanel mainPanel;
    public Gui() {
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        playerControlPanel = new PlayerControlPanel();
        mainPanel.add(playerControlPanel, BorderLayout.SOUTH);
    }

    public PlayerControlPanel getPlayerControlPanel() {
        return playerControlPanel;
    }
}
