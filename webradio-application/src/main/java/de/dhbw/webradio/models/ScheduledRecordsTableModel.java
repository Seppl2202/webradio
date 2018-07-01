package de.dhbw.webradio.models;

import de.dhbw.webradio.recording.ScheduledRecord;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScheduledRecordsTableModel extends AbstractTableModel {
    private String[] headers = {"Interpret", "Titel"};
    private List<ScheduledRecord> scheduledRecords;

    public ScheduledRecordsTableModel(List<ScheduledRecord> recordsList) {
        super();
        this.scheduledRecords = recordsList;
    }

    @Override
    public int getRowCount() {
        return scheduledRecords.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ScheduledRecord r = scheduledRecords.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getActor();
            case 1:
                return r.getTitle();
                default:return null;
        }


    }

    public void addRow(ScheduledRecord r) {
        for (ScheduledRecord record : scheduledRecords) {
            if (record.equals(r)) {
                throw new IllegalArgumentException("Scheduled Record " + r.toString() + " already exists");
            } else {
                scheduledRecords.add(r);
                fireTableDataChanged();
            }
        }
    }

    public ScheduledRecord getScheduledRecordFromIndex(int index) {
        return scheduledRecords.get(index);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
