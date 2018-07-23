package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.eventhandlers.FilePathEventHandler;
import de.dhbw.webradio.settings.GeneralSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GeneralSettingsWindow extends JFrame {
    private GeneralSettings generalSettings;
    private Map<JLabel, JTextField> settingsInputElements;
    private JLabel filePathLabel;
    private JPanel elementsPanel, buttonsPanel;
    private JButton save, cancel, selectFilePath;

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
        elementsPanel.setLayout(new GridLayout(generalSettings.getAttributes().size() + 1, generalSettings.getAttributes().size() + 1));
        buttonsPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(elementsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        save = new JButton("Speichern");
        cancel = new JButton("Abbrechen");
        cancel.addActionListener(e -> this.dispose());
        buttonsPanel.add(save);
        buttonsPanel.add(cancel);

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
        filePathLabel = new JLabel("Verzeichnis für Audioaufnahmen: ");
        selectFilePath = new JButton("Ändern");
        selectFilePath.addActionListener(new FilePathEventHandler());
        elementsPanel.add(filePathLabel);
        elementsPanel.add(selectFilePath);
    }
}
