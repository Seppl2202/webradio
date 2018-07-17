package de.dhbw.webradio.gui;

import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.recording.RecorderController;

import javax.swing.*;
import java.awt.*;

public class AddScheduledRecordWindow extends JFrame {
    private JButton add, cancel;
    private JLabel artistLabel, titleLabel;
    private JTextField artistField, titleField;

    public AddScheduledRecordWindow() {
        initializeFrame();
    }

    private void initializeFrame() {
        this.setLayout(new GridLayout(3, 2));
        artistLabel = new JLabel("Interpret: ");
        titleLabel = new JLabel("Titel: ");
        artistField = new JTextField();
        titleField = new JTextField();
        add = new JButton("Hinzufügen");
        cancel = new JButton("Abbrechen");
        this.add(artistLabel);
        this.add(artistField);
        this.add(titleLabel);
        this.add(titleField);
        this.add(add);
        this.add(cancel);
        add.addActionListener(e -> saveScheduledRecord());
        cancel.addActionListener(e -> this.dispose());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.pack();

    }

    private void saveScheduledRecord() {
        if (checkInputValues()) {
            RecorderController.getInstance().addScheduledRecord(new ScheduledRecord(titleField.getText(), artistField.getText()));
            Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getScheduledRecordsTableModel().fireTableDataChanged();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie einen gültigen Interpret und/oder Titel ein", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("artist or title input did not match the specifications");
        }
    }

    private boolean checkInputValues() {
        if (artistField.getText().equals(null) || artistField.getText().equals("") || titleField.getText().equals(null)|| titleField.getText().equals(null)) {
            return false;
        }
        return true;
    }
}
