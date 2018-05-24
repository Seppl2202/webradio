package de.dhbw.webradio.settings;

import java.util.HashMap;
import java.util.Map;

public class RecordSettings {
    private Map<String, String> attributes = new HashMap<String, String>();

    public RecordSettings() {
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
