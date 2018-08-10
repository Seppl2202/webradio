package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.gui.GeneralSettingsWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralSettingsEventHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new GeneralSettingsWindow();
    }
}
