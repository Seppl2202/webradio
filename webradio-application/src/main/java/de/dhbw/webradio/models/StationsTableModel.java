package de.dhbw.webradio.models;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StationsTableModel extends AbstractTableModel {
    private String headers[] = {"Name", "URL", "Löschen"};
    private List<Station> stationList;

    public StationsTableModel(List<Station> stations) {
        super();
        this.stationList = stations;
    }

    @Override
    public int getRowCount() {
        return stationList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Station s = stationList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getName();
            case 1:
                return s.getStationURL();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return this.headers[column];
    }

    public void addRow(Station s) {
        for (Station station : stationList
                ) {
            if (station.equals(s)) {
                throw new IllegalArgumentException("Station " + s.toString() + " already exists in list");
            } else {
                stationList.add(s);
                fireTableDataChanged();
            }

        }
    }
    public Station getStationFromIndex(int row) {
        return stationList.get(row);
    }

    //editing cells is forbidden
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
