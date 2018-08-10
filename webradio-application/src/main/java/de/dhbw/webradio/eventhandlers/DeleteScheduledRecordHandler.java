package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.recording.RecorderController;
import de.dhbw.webradio.models.ScheduledRecord;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeleteScheduledRecordHandler implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_DELETE) {
            deleteScheduledRecord();
        }
    }

    private void deleteScheduledRecord() {
        ScheduledRecord r = getSelectedRecord();
        Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getScheduledRecordsTableModel().fireTableDataChanged();
        WebradioPlayer.deleteScheduledRecord(r);
        Logger.logInfo("Deleted scheduled record: " + r.toString());
    }

    private ScheduledRecord getSelectedRecord() {
        int selectedRow = Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getRecordsTable().getSelectedRow();
        if (Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getRecordsTable().getRowSorter() != null) {
            int realRow = Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getRecordsTable().getRowSorter().convertRowIndexToModel(selectedRow);
            return Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getScheduledRecordsTableModel().getScheduledRecordFromIndex(realRow);
        }
        return Gui.getInstance().getRecorderTab().getScheduledRecordsWindow().getTable().getScheduledRecordsTableModel().getScheduledRecordFromIndex(selectedRow);
    }
}
