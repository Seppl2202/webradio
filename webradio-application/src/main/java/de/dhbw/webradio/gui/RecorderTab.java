package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.recording.Recorder;
import de.dhbw.webradio.recording.RecorderController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecorderTab extends JPanel {
    private Recorder r;
    private JButton start;
    private ScheduledRecordsWindow window;

    public RecorderTab() {
        initializePanel();
    }

    private void initializePanel() {
        start = new JButton("Start");
        start.setEnabled(false);
        this.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r = RecorderController.getInstance().recordNow(WebradioPlayer.getPlayer().getUrl());
                if (RecorderController.getInstance().getActualRecordNowRecorder() == null) {
                    startRecording();
                    switchState();
                } else {
                    if (RecorderController.getInstance().getActualRecordNowRecorder().isRecording()) {
                        stopRecording();
                        switchState();
                    } else {
                        startRecording();
                        switchState();
                    }
                }
            }
        });
        JButton scheduledRecords = new JButton("Aufnahme nach Titel...");
        scheduledRecords.addActionListener(e -> this.window = new ScheduledRecordsWindow());
        this.add(scheduledRecords);
    }

    private void startRecording() {
        RecorderController.getInstance().setActualRecordNowRecorder(r);
        r.recordNow(WebradioPlayer.getPlayer().getUrl());
    }

    private void stopRecording() {
        System.err.println("stopped recording");
        RecorderController.getInstance().getActualRecordNowRecorder().stop();
    }

    private void switchState() {
        if (this.start.getText().equalsIgnoreCase("start")) {
            this.start.setText("Stop");
        } else {
            this.start.setText("Start");
        }
    }

    public ScheduledRecordsWindow getScheduledRecordsWindow() {
        return this.window;
    }

    public void toggleControls(boolean statusToSet) {
        if (WebradioPlayer.getPlayer().isPlaying()) {
            this.start.setEnabled(statusToSet);
        }
    }
}
