package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.settings.GeneralSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GeneralSettingsWindow extends JFrame {
    private GeneralSettings generalSettings;
    private Map<JLabel, JTextField> settingsInputElements;
    private JPanel elementsPanel, buttonsPanel;
    private JButton save, cancel;

    public GeneralSettingsWindow() {
        settingsInputElements = new HashMap<JLabel, JTextField>();
        generalSettings = loadGeneralSettings();
        initializeFrame();
        addElementsToMap();
        addElementsToGui();
    }

    private void initializeFrame() {
        this.setTitle("Allgemeine Einstellungen");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        this.add(mainPanel);
        elementsPanel = new JPanel();
        elementsPanel.setLayout(new GridLayout(generalSettings.getAttributes().size(), generalSettings.getAttributes().size()));
        buttonsPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(elementsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        save = new JButton("Speichern");
        cancel = new JButton("Abbrechen");
        buttonsPanel.add(save);
        buttonsPanel.add(cancel);
        cancel = new JButton("Abbrechen");

    }

    private void updateInputValues() {
    }

    private GeneralSettings loadGeneralSettings() {
        return WebradioPlayer.getSettings().getGeneralSettings();
    }

    private void addElementsToMap() {
        for (Map.Entry<String, String> settings : generalSettings.getAttributes().entrySet()) {
            JLabel label = new JLabel(settings.getKey());
            JTextField textField = new JTextField(settings.getValue());
            settingsInputElements.put(label, textField);
        }
    }

    private void addElementsToGui() {
        for (Map.Entry<JLabel, JTextField> input : settingsInputElements.entrySet()) {
            elementsPanel.add(input.getKey());
            elementsPanel.add(input.getValue());
        }
    }
}
