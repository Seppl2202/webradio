package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.gui.AddStationWindow;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.playlistparser.FileExtensionParser;
import de.dhbw.webradio.models.Station;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddStationEventHandler implements ActionListener {
    private Map<JLabel, JTextField> inputElements;
    private AddStationWindow window;

    public AddStationEventHandler(Map<JLabel, JTextField> inputElements, AddStationWindow windowPassed) {
        if (!(windowPassed instanceof AddStationWindow)) {
            throw new IllegalArgumentException("unsopported class type passed! required: " + AddStationWindow.class + " but found: " + windowPassed.getClass());
        }
        this.inputElements = inputElements;
        this.window = windowPassed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!saveStation()) {
            JOptionPane.showMessageDialog(Gui.getInstance(), "Es ist ein Fehler aufgetreten", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            window.dispose();
        }
    }

    private boolean saveStation() {
        List<JLabel> keys = new ArrayList<>();
        for (Map.Entry<JLabel, JTextField> inputElement : inputElements.entrySet()) {
            keys.add(inputElement.getKey());
        }
        String stationName = inputElements.get(keys.get(0)).getText();
        String urlString = inputElements.get(keys.get(1)).getText();
        URL stationURL = null;
        try {
            stationURL = new URL(urlString);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(window, "Dies ist keine valide URL!", "URL nicht valide", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
        Station s = new Station(stationName, stationURL);
        if (checkStation(s)) {
            return WebradioPlayer.addStation(s);
        }
        return false;
    }

    private boolean checkStation(Station s) {
        FileExtensionParser parser = new FileExtensionParser();
        try {
            if (parser.parseFileExtension(s.getStationURL()).equals(FileExtension.UNSUPPORTED_TYPE)) {
                JOptionPane.showMessageDialog(null, "Dieses Dateiformat wird nicht unterstützt. \r\n" +
                        "Folgende Formate werden unterstützt:\r\n" +
                        "MP3, AAC, M3U, PLS");
            }
            return s.isURLValid();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "Die URL konnte nicht erreicht werden. Bitte prüfen!", "Verbindungsfehler", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
