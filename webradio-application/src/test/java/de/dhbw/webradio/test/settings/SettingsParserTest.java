package de.dhbw.webradio.test.settings;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.settings.GeneralSettings;
import de.dhbw.webradio.settings.Settings;
import de.dhbw.webradio.settings.SettingsParser;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SettingsParserTest {


    @Test
    public void parsegeneralSettings() {
        SettingsParser parser = new SettingsParser();
        Settings generalSettings = parser.parsegeneralSettings(WebradioPlayer.settingsDirectory);
        assertEquals(generalSettings.getGeneralSettings().getAttributes().get("bufferSize"), "1024");
    }
}
