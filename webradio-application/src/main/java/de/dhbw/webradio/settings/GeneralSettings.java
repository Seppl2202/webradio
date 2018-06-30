package de.dhbw.webradio.settings;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GeneralSettings {
    private Map<String, String> attributes = new HashMap<String, String>();
    private File directory;


    public GeneralSettings() {
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setDirectory(File f) {
        this.directory = f;
    }

    public File getRecordingDirectory() {
        return this.directory;
    }
}
