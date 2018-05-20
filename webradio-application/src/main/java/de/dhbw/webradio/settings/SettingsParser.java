package de.dhbw.webradio.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class SettingsParser {

    public SettingsParser() {

    }

    public GeneralSettings parsegeneralSettings(File file) {
        GeneralSettings generalSettings = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            generalSettings = mapper.readValue(file, GeneralSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return generalSettings;
    }
}
