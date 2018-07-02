package de.dhbw.webradio.recording;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.models.ScheduledRecord;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecorderController {
    private static RecorderController recorderController = new RecorderController();
    private Recorder actualRecordNowRecorder;
    private List<ScheduledRecord> scheduledRecordList = new ArrayList<>();
    private List<Recorder> listeningRecorders = new ArrayList<>();

    private RecorderController() {

    }

    public static RecorderController getInstance() {
        return recorderController;
    }

    public Recorder recordNow(URL url) {
        FileExtensionParser p = new FileExtensionParser();
        FileExtension mediaType = p.parseFileExtension(url);

        if (mediaType.equals(FileExtension.MP3)) {
            return new MP3Record();
        }
        if (mediaType.equals(FileExtension.AAC)) {
            return new AACRecorder();
        }
        return actualRecordNowRecorder;
    }

    public Recorder getActualRecordNowRecorder() {
        return actualRecordNowRecorder;
    }

    public void setActualRecordNowRecorder(Recorder r) {
        this.actualRecordNowRecorder = r;
    }

    public List<ScheduledRecord> getScheduledRecordList() {
        return scheduledRecordList;
    }

    public void addScheduledRecord(ScheduledRecord r) {
        scheduledRecordList.add(r);
    }

    public void removeScheduledRecord(ScheduledRecord r) {
        scheduledRecordList.remove(r);
    }
}
