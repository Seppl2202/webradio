package de.dhbw.webradio.gui;


import de.dhbw.webradio.eventhandlers.AddStationEventHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddStationWindow extends JFrame {
    private Map<JLabel, JTextField> inputElements;
    private JPanel mainPanel;
    private JButton save, cancel;
    private List<JLabel> inputLabels;
    public AddStationWindow() {
        inputElements = new LinkedHashMap<JLabel, JTextField>();
        inputLabels = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        mainPanel = new JPanel(new BorderLayout());
        addInputElementsToMap();
        generateInputElements();
        save = new JButton("Speichern");
        save.addActionListener(new AddStationEventHandler(inputElements, this));
        cancel = new JButton("Abbrechen");
        cancel.addActionListener(e -> this.dispose());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(save);
        buttonsPanel.add(cancel);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.setTitle("Sender hinzufügen");
        this.setSize(800,150);
        this.add(mainPanel);
    }

    private void generateInputElements() {
        JPanel elementsPanel = new JPanel();
        elementsPanel.setLayout(new GridLayout(inputElements.size(), inputElements.size()));
        for (Map.Entry<JLabel, JTextField> inputElement : inputElements.entrySet()) {
            elementsPanel.add(inputElement.getKey());
            inputLabels.add(inputElement.getKey());
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

    public List<JLabel> getInputLabels() {
        return inputLabels;
    }
}
