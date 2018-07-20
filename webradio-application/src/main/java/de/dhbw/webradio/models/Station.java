package de.dhbw.webradio.models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Station {
    private String name;
    private URL stationURL;
    private String m3uStationName;

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

        if (statusCode == 301 || statusCode == 302 || statusCode == 303) {
            String newURL = connection.getHeaderField("Location");
            connection = (HttpURLConnection) new URL(newURL).openConnection();
            statusCode = connection.getResponseCode();
            System.err.println("Followed redirect from: " + stationURL.toString() + " to: " + newURL);
            this.stationURL = new URL(newURL);
        }
        if (!(statusCode == 200)) {
            return false;
        }
        return true;
    }

    public String getM3uStationName() {
        return m3uStationName;
    }

    public void setM3uStationName(String m3uStationName) {
        this.m3uStationName = m3uStationName;
    }

    @Override
    public boolean equals(Object obj) {
        Station o = (Station) obj;
        return name.equalsIgnoreCase((o.name)) &&
                stationURL.equals(( o.stationURL));
    }
}
