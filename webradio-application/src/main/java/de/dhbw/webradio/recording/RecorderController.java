package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.m3uparser.FileExtensionParser;
import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.MetainformationReader;
import de.dhbw.webradio.radioplayer.SimpleIcyInputStreamReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecorderController {
    private static RecorderController recorderController = new RecorderController();
    private Recorder actualRecordNowRecorder = null;
    private List<ScheduledRecord> scheduledRecordList = new ArrayList<>();
    private List<Recorder> listeningRecorders = new ArrayList<>();
    private int maximumRecords = 5;

    private RecorderController() {
        initializeScheduledRecords();
    }

    public static RecorderController getInstance() {
        return recorderController;
    }

    public Recorder recordNow(URL url) {
        FileExtensionParser p = new FileExtensionParser();
        FileExtension mediaType = p.parseFileExtension(url);

        if (mediaType.equals(FileExtension.MP3)) {
            return new MP3Recorder();
        }
        if (mediaType.equals(FileExtension.AAC)) {
            return new AACRecorder();
        }
        return actualRecordNowRecorder;
    }

    private void initializeScheduledRecords() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (scheduledRecordList.size() <= maximumRecords && i < WebradioPlayer.getStationList().size()) {
                    Station s = WebradioPlayer.getStationList().get(i);
                    MetainformationReader r = null;
                    try {
                        r = new SimpleIcyInputStreamReader(s.getStationURL());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread t = new Thread(r);
                    t.start();
                    waitToFetchIcy();
                    if (!(r.getActualMusicTitle().equalsIgnoreCase("keine informationen verfügbar"))) {
                        Recorder recorder = RecorderController.getInstance().recordNow(s.getStationURL());
                        recorder.setMetaInformationReader(r);
                        listeningRecorders.add(recorder);
                        recorder.recordNow(s.getStationURL());
                        Logger.logInfo("Added new listening recorder: " + recorder.toString());
                    }
                    i++;
                }
            }
        }).start();
    }

    private void waitToFetchIcy() {
        try {
            Thread.sleep(30000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
