package de.dhbw.webradio.models;

import javax.swing.table.AbstractTableModel;
import java.util.Iterator;
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
            default:
                return null;
        }


    }

    public void addRow(ScheduledRecord r) {
        if (!(scheduledRecords.size() == 0)) {
            ScheduledRecord toAdd = new ScheduledRecord("Fehler", "Fehler");
            for (Iterator<ScheduledRecord> recordIterator = scheduledRecords.iterator(); recordIterator.hasNext(); ) {
                ScheduledRecord record = recordIterator.next();
                if (record.equals(r)) {
                    throw new IllegalArgumentException("Scheduled Record " + r.toString() + " already exists");
                } else {
                    toAdd = record;
                }
            }
            scheduledRecords.add(toAdd);
        } else {
            scheduledRecords.add(r);
        }
    }

    public void removeRow(ScheduledRecord r) {
        scheduledRecords.remove(r);
        fireTableDataChanged();
    }

    public ScheduledRecord getScheduledRecordFromIndex(int index) {
        return scheduledRecords.get(index);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
