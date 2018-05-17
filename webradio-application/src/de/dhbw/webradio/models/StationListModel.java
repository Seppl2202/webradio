package de.dhbw.webradio.models;

import java.net.MalformedURLException;
import java.net.URL;

public class StationListModel {


    public static Station getSelectedStation() {
        try {
            return new Station("FFH", new URL("http://mp3.ffh.de/radioffh/hqlivestream.mp3"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
