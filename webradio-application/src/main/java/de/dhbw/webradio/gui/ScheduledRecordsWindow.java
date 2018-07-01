package de.dhbw.webradio.gui;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarFile;

public class ScheduledRecordsWindow extends JFrame {
    private ScheduledRecordsTable table;
    private JButton add;

    public ScheduledRecordsWindow(){
        initializeFrame();
    }

    private void initializeFrame() {
        table = new ScheduledRecordsTable();
        this.setLayout(new BorderLayout());
        this.add(table, BorderLayout.CENTER);
        add = new JButton();
        this.add(add, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public ScheduledRecordsTable getTable() {
        return table;
    }
}
