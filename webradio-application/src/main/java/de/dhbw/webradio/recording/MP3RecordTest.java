package de.dhbw.webradio.recording;

import de.dhbw.webradio.WebradioPlayer;
import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1;
import de.dhbw.webradio.radioplayer.PlayerFactory;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MP3RecordTest implements Recorder, Runnable {
    private boolean stop = false;
    private File recorderDirectory = WebradioPlayer.getSettings().getGeneralSettings().getRecordingDirectory();

    @Override
    public void recordNow(URL url, String filename) throws LineUnavailableException, IOException {
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
                    System.err.println("file: " + f.toString());
                    System.err.println("path: " + path.toString());
                    stop = false;
                    OutputStream out = new FileOutputStream(path.toFile());
                    byte[] buffer = new byte[4096];
                    int len;
                    long t = System.currentTimeMillis();
                    while ((len = in.read(buffer)) > 0 && (!stop)) {
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
                ID3 id3 = new ID3v1();
                id3.setTitle(createTitle());
                id3.setArtist(createArtist());
                id3.setGenre(3);
                id3.setYear("2018");
                id3.setAlbum("Testalbum");
                id3.setComment("None");
                id3.setTrack("15");
                id3.writeId3Info();
                System.err.println(id3.toString());
                id3.writeId3ToFile(f);
                System.err.println("finished writing id3");
            }
        }).start();
    }

    private String createTitle() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        return s.split("-")[0];
    }

    private String createArtist() {
        String s = WebradioPlayer.getPlayer().getIcyReader().getActualMusicTitle();
        return s.split("-")[1];
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
        this.stop = true;
    }

    @Override
    public void run() {

    }
}
