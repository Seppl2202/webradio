package de.dhbw.webradio.recording;

import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.m3uparser.FileExtensionParser;

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

    public Recorder recordNow(URL url, String filename) {
        FileExtensionParser p = new FileExtensionParser();
        FileExtension mediaType = p.parseFileExtension(url);

        switch (mediaType) {
            case MP3:
                actualRecordNowRecorder = new MP3RecordTest();
            case AAC:
//                actualRecordNowRecorder = new AACRecorder();
        }

        return actualRecordNowRecorder;
    }
}
