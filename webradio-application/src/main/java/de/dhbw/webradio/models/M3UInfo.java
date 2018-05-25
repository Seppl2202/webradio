package de.dhbw.webradio.models;

import java.net.URL;

public class M3UInfo {
    private URL url;
    private String titleInfo;

    public M3UInfo(URL url, String titleInfo) {
        this.url = url;
        this.titleInfo = titleInfo;
    }

    public URL getUrl() {
        return url;
    }

    public String getTitleInfo() {
        return titleInfo;
    }

    @Override
    public String toString() {
        return getTitleInfo();
    }
}
