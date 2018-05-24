package de.dhbw.webradio.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class SettingsParser {

    private Settings settings;

    public SettingsParser() {

    }

    public Settings parsegeneralSettings(File file) {
        Settings Settings = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            settings = mapper.readValue(file, Settings.class);
            System.err.println(settings.getGeneralSettings().getAttributes().toString());
            System.err.println(settings.getRecordSettings().getAttributes().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }
}
