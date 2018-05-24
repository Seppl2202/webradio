package de.dhbw.webradio.settings;

import java.util.HashMap;
import java.util.Map;

public class GeneralSettings {
    private Map<String, String> attributes = new HashMap<String, String>();


    public GeneralSettings() {
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
