package de.dhbw.webradio.models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Station {
    private String name;
    private URL stationURL;

    public Station(String name, URL stationURL) {
        this.name = name;
        this.stationURL = stationURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getStationURL() {
        return stationURL;
    }

    public void setStationURL(URL stationURL) {
        this.stationURL = stationURL;
    }

    public boolean isURLValid() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) stationURL.openConnection();
        connection.setRequestMethod("HEAD");
        connection.connect();
        int statusCode = connection.getResponseCode();

        if (!(statusCode == 200)) {
            return false;
        }
        return true;
    }
}
