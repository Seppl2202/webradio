package de.dhbw.webradio.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.dhbw.webradio.WebradioPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SettingsWriter implements Writer{

    @Override
    public void updateFilePath(File f) {
        GeneralSettings generalSettings = WebradioPlayer.getSettings().getGeneralSettings();
        generalSettings.setDirectory(f);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(WebradioPlayer.settingsDirectory, WebradioPlayer.getSettings());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
