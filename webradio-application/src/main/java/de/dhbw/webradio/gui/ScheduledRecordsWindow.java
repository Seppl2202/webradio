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
        add = new JButton("Hinzufügen");
        this.add(add, BorderLayout.SOUTH);
        add.addActionListener(e-> new AddScheduledRecordWindow());
        this.setVisible(true);
        this.setSize(800,350);
    }

    public ScheduledRecordsTable getTable() {
        return table;
    }
}
