package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1Builder;
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
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MP3Recorder implements Recorder, Runnable {
    private boolean recording = false;
    private File recorderDirectory = WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory();
    private MetainformationReader reader = null;

    @Override
    public void recordNow(URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (url == null) {
                    throw new IllegalArgumentException("URL was not valid");
                }
                File f = null;
                try {
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream in = con.getInputStream();
                    f = new File(recorderDirectory + "/" + generateFileName() + ".mp3");
                    Path path = Paths.get(f.toURI());
                    recording = true;
                    System.err.println("started mp3 recording from: " + url.toString());
                    OutputStream out = new FileOutputStream(path.toFile());
                    byte[] buffer = new byte[4096];
                    int len;
                    long t = System.currentTimeMillis();
                    while ((len = in.read(buffer)) > 0 && (recording)) {
                        out.write(buffer, 0, len);
                    }
                    out.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Logger.logInfo("Finished mp3 recording");
                Logger.logInfo("writing id3v1.1");
                ID3 id3 = new ID3v1Builder(createTitle().orElse("Webradio-Aufnahme"), createArtist().orElse("Kein Interpret"))
                        .setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).toString())
                        .setAlbum(WebradioPlayer.getPlayer().getIcyReader().getStationName())
                        .setGenre(1)
                        .build();
                id3.writeId3Info();
                id3.writeId3ToFile(f);
                Logger.logInfo("Wrote ID3: " + id3.toString());
                Logger.logInfo("finished writing id3 for file " + f.toString());
            }
        }).start();
    }

    private Optional<String> createTitle() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        String title = null;
        if (s.contains("/")) {
            title = s.split("/")[0].trim();
        } else if (s.contains("-")) {
            title = s.split("-")[0].trim();
        }
        return Optional.ofNullable(title);
    }

    private Optional<String> createArtist() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        String artist = null;
        if (s.contains("/")) {
            artist = s.split("/")[1].trim();
        } else if (s.contains("-")) {
            artist = s.split("-")[1].trim();
        }
        return Optional.ofNullable(artist);
    }

    private String generateFileName() {
        return FileUtilitie.generateFileNameForRecording();
    }

    @Override
    public void recordByTitle(URL url, Recorder recorder) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ScheduledRecord matchedRecord = null;
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
        }, 1, 1, TimeUnit.SECONDS);

    }


    @Override
    public void stop() {
        this.recording = false;
    }

    @Override
    public void run() {

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
