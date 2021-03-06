package de.dhbw.webradio.gui;

import javax.swing.*;
import java.awt.*;

public class StreamDetails extends JPanel {
    private final String M3UTEXT = "Titelinformationen: ";
    private final String M3UURL = "URL des Senders: ";
    private final String STREAMURL = "URL des Audiostreams: ";
    private final String STATIONNAME = "Sendername Ihrer Liste: ";
    private final String CLEAN_TITEL_INFO = "Echter Titel: ";
    //
    private JLabel m3uInfo;
    private JLabel m3uUrl;
    private JLabel streamUrl;
    private JLabel stationName;
    private JLabel realTitel;

    StreamDetails() {
        initializePanel();
    }

    private void initializePanel() {
        this.setLayout(new GridLayout(5, 1));
        m3uInfo = new JLabel(M3UTEXT);
        m3uUrl = new JLabel(M3UURL);
        streamUrl = new JLabel(STREAMURL);
        stationName = new JLabel(STATIONNAME);
        realTitel = new JLabel(CLEAN_TITEL_INFO);
        this.add(m3uInfo);
        this.add(m3uUrl);
        this.add(streamUrl);
        this.add(stationName);
        this.add(realTitel);
    }

    public void updateM3uInfo(String s) {
        m3uInfo.setText(M3UTEXT + s);
    }

    public void updateM3uUrl(String url) {
        m3uUrl.setText(M3UURL + url);
    }

    public void updateStreamUrl(String url) {
        streamUrl.setText(STREAMURL + url);
    }

    public void updateStationName(String s) {
        stationName.setText(STATIONNAME + s);
    }

    public void updateRealTitelInfo(String s) {
        realTitel.setText(CLEAN_TITEL_INFO + s);
    }
}
