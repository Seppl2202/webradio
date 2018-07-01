package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.gui.Gui;
import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1;
import de.dhbw.webradio.id3.ID3v1Builder;
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

public class MP3RecordTest implements Recorder, Runnable {
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
                System.err.println("writing id3v1.1");
                ID3v1Builder builder = new ID3v1Builder("Testttiel", "Testartist");
                builder.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).toString());
                builder.setAlbum(WebradioPlayer.getPlayer().getIcyReader().getStationName());
                ID3v1 id3 = builder.build();
                System.err.println(id3.toString());
                id3.writeId3Info();
                id3.writeId3ToFile(f);
                System.err.println("finished writing id3");
            }
        }).start();
    }

    private String createTitle() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        if (s.contains("/")) {
            return s.split("'/'")[0];
        } else if (s.contains("-")) {
            return s.split("-")[0];
        }
        return "No title macthed";
    }

    private String createArtist() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        if (s.contains("/")) {
            return s.split("'/'")[1];
        } else if (s.contains("-")) {
            return s.split("-")[1];
        }
        return "No artist macthed";
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
