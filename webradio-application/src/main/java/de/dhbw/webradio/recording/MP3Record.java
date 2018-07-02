package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1;
import de.dhbw.webradio.id3.ID3v1Builder;
import de.dhbw.webradio.logger.Logger;
import de.dhbw.webradio.radioplayer.PlayerFactory;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;

public class MP3Record implements Recorder, Runnable {
    private boolean recording = false;
    private File recorderDirectory = WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory();

    @Override
    public void recordNow(URL url) throws LineUnavailableException, IOException {
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
                    System.err.println("started recording");
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
            title = s.split("/")[0];
        } else if (s.contains("-")) {
            title =  s.split("-")[0];
        }
        return Optional.ofNullable(title);
    }

    private Optional<String> createArtist() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        String artist = null;
        if (s.contains("/")) {
            artist =  s.split("/")[1];
        } else if (s.contains("-")) {
            artist=  s.split("-")[1];
        }
        //replace possible spaces, tabs and line wraps after slash splitting
        artist.replaceFirst("\\s", "");
        return Optional.ofNullable(artist);
    }

    private String generateFileName() {
        //remove everything but characters and numbers
        return WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle().replaceAll("[^a-zA-Z0-9]", "");
    }

    @Override
    public void recordByTitle() {

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
}
