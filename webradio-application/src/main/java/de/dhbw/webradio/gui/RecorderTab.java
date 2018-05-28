package de.dhbw.webradio.gui;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.recording.AACRecorder;
import de.dhbw.webradio.recording.Recorder;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RecorderTab extends JPanel {
    private Recorder r;

    public RecorderTab() {
        initializePanel();
    }

    private void initializePanel() {
        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r = new AACRecorder(WebradioPlayer.getPlayer().getUrl(), new File("C://repository/rec.wav"));
                try {
                    r.recordNow();
                } catch (LineUnavailableException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton stop = new JButton("stop");
        stop.addActionListener(e -> r.stop());
        this.add(start);
        this.add(stop);
    }
}
