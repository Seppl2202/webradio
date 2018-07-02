package de.dhbw.webradio.eventhandlers;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.settings.SettingsWriter;
import de.dhbw.webradio.settings.Writer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FilePathEventHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        createDialogAndSaveUserSelection();
    }

    private void createDialogAndSaveUserSelection() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory());
        int returnValue = chooser.showOpenDialog(Gui.getInstance());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File userSelectedDirectory = chooser.getSelectedFile();
            saveUserSelection(userSelectedDirectory);
        }
    }

    private void saveUserSelection(File f) {
        Writer settingsWriter = new SettingsWriter();
        settingsWriter.updateFilePath(f);
        Logger.logInfo("saved new directory" + f.toString());
    }
}
