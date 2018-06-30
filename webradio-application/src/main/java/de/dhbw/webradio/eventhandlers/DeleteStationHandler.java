package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.models.Station;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DeleteStationHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_DELETE) {
            removeStationAndUpdateTableModel();
        }
    }

    private void removeStationAndUpdateTableModel() {
        Station toDelete = getStation();
        WebradioPlayer.deleteStation(toDelete);
        Gui.getInstance().getStationsTableModel().fireTableDataChanged();
        System.err.println("removed " + toDelete);
    }


    private Station getStation() {
        if (Gui.getInstance().getStationsTable().getRowSorter() != null) {
            int selectedRow = Gui.getInstance().getStationsTable().getSelectedRow();
            int realRow = Gui.getInstance().getStationsTable().getRowSorter().convertRowIndexToModel(selectedRow);
            return Gui.getInstance().getStationsTableModel().getStationFromIndex(realRow);
        }
        return Gui.getInstance().getStationsTableModel().getStationFromIndex(Gui.getInstance().getStationsTable().getSelectedRow());
    }
}
