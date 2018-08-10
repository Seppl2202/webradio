package de.dhbw.webradio.models;

import java.net.URL;

public class M3UInfo extends InformationObject {

    public M3UInfo(URL url, String titleInfo) {
        this.url = url;
        this.titleInfo = titleInfo;
    }


}
