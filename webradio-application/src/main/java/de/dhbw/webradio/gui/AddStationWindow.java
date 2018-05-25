package de.dhbw.webradio.gui;


import de.dhbw.webradio.eventhandlers.AddStationEventHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AddStationWindow extends JFrame {
    private Map<JLabel, JTextField> inputElements;
    private JPanel mainPanel;
    private JButton save, cancel;

    public AddStationWindow() {
        inputElements = new LinkedHashMap<JLabel, JTextField>();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        mainPanel = new JPanel(new BorderLayout());
        addInputElementsToMap();
        generateInputElements();
        save = new JButton("Speichern");
        save.addActionListener(new AddStationEventHandler(this));
        cancel = new JButton("Abbrechen");
        cancel.addActionListener(e -> this.dispose());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(save);
        buttonsPanel.add(cancel);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.setTitle("Sender hinzufügen");
        this.add(mainPanel);
    }

    private void generateInputElements() {
        JPanel elementsPanel = new JPanel();
        elementsPanel.setLayout(new GridLayout(inputElements.size(), inputElements.size()));
        for (Map.Entry<JLabel, JTextField> inputElement : inputElements.entrySet()) {
            elementsPanel.add(inputElement.getKey());
            elementsPanel.add(inputElement.getValue());
        }
        mainPanel.add(elementsPanel, BorderLayout.CENTER);
    }

    private void addInputElementsToMap() {
        inputElements.put(new JLabel("Name"), new JTextField());
        inputElements.put(new JLabel("URL"), new JTextField());
    }

    public Map<JLabel, JTextField> getInputElements() {
        return inputElements;
    }
}
