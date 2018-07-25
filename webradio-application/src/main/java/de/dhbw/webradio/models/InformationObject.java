package de.dhbw.webradio.models;

import java.net.URL;

public abstract class InformationObject {
    protected URL url;
    protected String titleInfo;

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
        if (!(obj instanceof InformationObject)) {
            return false;
        }
        InformationObject that = (InformationObject) obj;
        return this.getUrl().equals(that.getUrl())
                && this.getTitleInfo().equalsIgnoreCase(that.getTitleInfo());
    }
}
