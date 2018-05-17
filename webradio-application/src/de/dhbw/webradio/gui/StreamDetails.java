package de.dhbw.webradio.gui;

import javax.swing.*;
import java.awt.*;

public class StreamDetails extends JPanel {
    private final String channelsText = "Anzahl Kanäle: ";
    private final String sampleRateText = "Bitrate: ";
    private final String formatText = "Format: ";
    private JLabel channels, sampleRate, format;

    StreamDetails() {
        initializePanel();
    }

    private void initializePanel() {
        channels = new JLabel(channelsText);
        sampleRate = new JLabel(sampleRateText);
        format = new JLabel(formatText);
        this.setLayout(new GridLayout(3,1));
        this.add(channels);
        this.add(sampleRate);
        this.add(format);
    }

    public void changeFormat(String format) {
        this.format.setText(formatText + format);
    }
    public void changeSamplerate(float samplerate) {
        this.sampleRate.setText(sampleRateText + samplerate);
    }
    public void changeChannelsText(int channelCount) {
        this.channels.setText(channelsText + channelCount);
    }
}
