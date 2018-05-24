package de.dhbw.webradio.settings;

public class Settings {
    private GeneralSettings generalSettings;
    private RecordSettings recordSettings;

    public GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    public void setGeneralSettings(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
    }

    public RecordSettings getRecordSettings() {
        return recordSettings;
    }

    public void setRecordSettings(RecordSettings recordSettings) {
        this.recordSettings = recordSettings;
    }
}
