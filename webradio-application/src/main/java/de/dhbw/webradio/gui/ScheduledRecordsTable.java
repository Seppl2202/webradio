package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.eventhandlers.DeleteScheduledRecordHandler;
import de.dhbw.webradio.models.ScheduledRecordsTableModel;
import de.dhbw.webradio.recording.RecorderController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ScheduledRecordsTable extends JPanel {
    private JTable recordsTable;
    private ScheduledRecordsTableModel scheduledRecordsTableModel;
    private JTextField searchField;

    public ScheduledRecordsTable() {
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new BorderLayout());
        searchField = new JTextField("Suche...");
        this.add(searchField, BorderLayout.NORTH);
        scheduledRecordsTableModel = new ScheduledRecordsTableModel(WebradioPlayer.getScheduledRecords());
        recordsTable = new JTable();
        recordsTable.setModel(scheduledRecordsTableModel);
        this.add(new JScrollPane(recordsTable), BorderLayout.CENTER);
        final TableRowSorter<ScheduledRecordsTableModel> rowSorter = new TableRowSorter<>(scheduledRecordsTableModel);
        recordsTable.setRowSorter(rowSorter);
        recordsTable.addKeyListener(new DeleteScheduledRecordHandler());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw  new UnsupportedOperationException("not implemented yet");
            }
        });
    }

    public JTable getRecordsTable() {
        return this.recordsTable;
    }

    public ScheduledRecordsTableModel getScheduledRecordsTableModel() {
        return scheduledRecordsTableModel;
    }
}
