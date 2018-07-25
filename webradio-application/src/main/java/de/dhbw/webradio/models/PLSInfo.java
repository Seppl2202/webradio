package de.dhbw.webradio.models;

import java.net.MalformedURLException;
import java.net.URL;

public class PLSInfo extends InformationObject {
    public PLSInfo(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.titleInfo = "PLS";
    }
}
