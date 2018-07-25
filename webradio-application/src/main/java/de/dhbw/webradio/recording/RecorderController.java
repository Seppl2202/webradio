package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.enumerations.FileExtension;
import de.dhbw.webradio.exceptions.NoURLTagFoundException;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.playlistparser.FileExtensionParser;
import de.dhbw.webradio.playlistparser.M3uParser;
import de.dhbw.webradio.playlistparser.PLSParser;
import de.dhbw.webradio.models.InformationObject;
import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.models.Station;
import de.dhbw.webradio.radioplayer.MetainformationReader;
import de.dhbw.webradio.radioplayer.SimpleIcyInputStreamReader;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
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
                    URL stationUrlString = null;
                    try {
                        stationUrlString = getStationURL(s);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    MetainformationReader r = null;
                    try {
                        r = new SimpleIcyInputStreamReader(stationUrlString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread t = new Thread(r);
                    t.start();
                    waitToFetchIcy();
                    if (!(r.getActualMusicTitle().equalsIgnoreCase("keine informationen verfügbar"))) {
                        Logger.logError("entered if");
                        Recorder recorder = RecorderController.getInstance().recordNow(stationUrlString);
                        recorder.setMetaInformationReader(r);
                        listeningRecorders.add(recorder);
                        recorder.recordByTitle(stationUrlString, recorder);
                        Logger.logInfo("Added new listenig recorder: " + recorder.toString());
                        i++;
                    }
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

    private URL getStationURL(Station s) throws MalformedURLException {
        FileExtensionParser fileExtensionParser = new FileExtensionParser();
        if (fileExtensionParser.parseFileExtension(s.getStationURL()).equals(FileExtension.AAC) || fileExtensionParser.parseFileExtension(s.getStationURL()).equals(FileExtension.MP3)) {
            return s.getStationURL();
        } else if (fileExtensionParser.parseFileExtension(s.getStationURL()).equals(FileExtension.M3U)) {
            M3uParser m3uParser = new M3uParser();
            try {
                String fileContent = m3uParser.parseFileFromUrlToString(s.getStationURL());
                List<InformationObject> m3uInfos = m3uParser.parseURLFromString(fileContent);
                if (m3uInfos.size() > 0) {
                    return m3uInfos.get(0).getUrl();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (NoURLTagFoundException e) {
                e.printStackTrace();
            }
        } else if (fileExtensionParser.parseFileExtension(s.getStationURL()).equals(FileExtension.PLS)) {
            PLSParser plsParser = new PLSParser();
            List<InformationObject> plsInfos = plsParser.parsePLS(s.getStationURL());

            return plsInfos.get(0).getUrl();
        }
        return new URL("http://notreachable.com");
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
