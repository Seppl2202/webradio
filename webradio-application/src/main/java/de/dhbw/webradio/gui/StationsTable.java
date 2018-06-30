package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.eventhandlers.DeleteStationHandler;
import de.dhbw.webradio.models.StationsTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StationsTable extends JPanel {
    private JTable stationsTable;
    private StationsTableModel stationsTableModel;
    private JTextField searchField;

    public StationsTable() {
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new BorderLayout());
        searchField = new JTextField("Suche...");
        this.add(searchField, BorderLayout.NORTH);
        stationsTableModel = new StationsTableModel(WebradioPlayer.getStationList());
        stationsTable = new JTable();
        stationsTable.setModel(stationsTableModel);
        this.add(stationsTable, BorderLayout.CENTER);
        final TableRowSorter<StationsTableModel> rowSorter = new TableRowSorter<>(stationsTableModel);
        stationsTable.setRowSorter(rowSorter);
        stationsTable.addKeyListener(new DeleteStationHandler());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.<StationsTableModel, Integer>regexFilter(text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if(text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.<StationsTableModel, Integer>regexFilter(text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });
    }

    public JTable getStationsTable() {
        return stationsTable;
    }

    public StationsTableModel getTableModel() {
        return stationsTableModel;
    }
}
