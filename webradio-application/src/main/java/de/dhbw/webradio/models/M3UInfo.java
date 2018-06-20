package de.dhbw.webradio.models;

import java.net.URL;

public class M3UInfo extends InformationObject {

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof M3UInfo)) {
            return false;
        }
        M3UInfo that = (M3UInfo) obj;
        return this.getUrl().equals(that.getUrl())
                && this.getTitleInfo().equalsIgnoreCase(that.getTitleInfo());
    }
}
