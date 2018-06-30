package de.dhbw.webradio.settings;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GeneralSettings {
    private Map<String, String> attributes = new HashMap<String, String>();
    private File recordingDirectory;


    public GeneralSettings() {
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setRecordingDirectory(File f) {
        this.recordingDirectory= f;
    }

    public File getRecordingDirectory() {
        return this.recordingDirectory;
    }
}
