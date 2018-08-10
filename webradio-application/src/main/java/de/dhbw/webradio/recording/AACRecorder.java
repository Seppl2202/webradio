package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.radioplayer.MetainformationReader;
import de.dhbw.webradio.utilities.FileUtilitie;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AACRecorder implements Recorder, Runnable {
    private boolean recording = false;
    private File recorderDirectory = WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory();
    private MetainformationReader reader = null;

    public AACRecorder() {
    }

    @Override
    public void recordNow(URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream in = con.getInputStream();
                    File f = new File(recorderDirectory + "/" + generateFileName() + ".aac");
                    Path path = Paths.get(f.toURI());
                    OutputStream out = new FileOutputStream(path.toFile());
                    byte[] buffer = new byte[4096];
                    int len;
                    Logger.logInfo("Started aac recording from: " + url.toString());
                    recording = true;
                    while ((len = in.read(buffer)) > 0 && (recording)) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    Logger.logInfo("Finished aac recording");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stop();
                }
            }
        }).start();
    }

    private String generateFileName() {
        //remove everything but characters and numbers
        return FileUtilitie.generateFileNameForRecording();
    }


    @Override
    public void recordByTitle(URL url, Recorder recorder) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ScheduledRecord matchedRecord = null;
                while (true) {
                    List<ScheduledRecord> scheduledRecords = RecorderController.getInstance().getScheduledRecordList();
                    for (ScheduledRecord r : scheduledRecords) {
                        if (reader.matchesScheduledRecord(r)) {
                            if (!r.equals(matchedRecord)) {
                                matchedRecord = r;
                                recorder.recordNow(url);
                                Logger.logInfo("Matched scheduled record: " + r.toString() + "in recorder: " + this.toString());
                            }
                        }
                    }
                    if (reader.matchesScheduledRecord(matchedRecord)) {
                        recorder.stop();
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }


    @Override
    public void run() {

    }

    @Override
    public void stop() {
        this.recording = false;
    }

    @Override
    public boolean isRecording() {
        return recording;
    }

    @Override
    public void setMetaInformationReader(MetainformationReader r) {
        this.reader = r;
    }

    @Override
    public String toString() {
        return "Recorder for: " + reader.getStationUrl();
    }
}
