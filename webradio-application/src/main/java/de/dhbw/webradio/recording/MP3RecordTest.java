package de.dhbw.webradio.recording;

import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MP3RecordTest implements Recorder, Runnable {
    private boolean stop = false;

    public static void main(String[] args) throws IOException, LineUnavailableException {

        MP3RecordTest r = new MP3RecordTest();
        r.recordNow();
    }

    @Override
    public void recordNow() throws LineUnavailableException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File f = null;
                try {
                    URL url = new URL("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream in = con.getInputStream();
                    f = new File("C://repository/recmp.mp3");
                    stop = false;
                    OutputStream out = new FileOutputStream(f);
                    byte[] buffer = new byte[4096];
                    int len;
                    long t = System.currentTimeMillis();
                    System.err.println("entering while");
                    while ((len = in.read(buffer)) > 0 && (System.currentTimeMillis() - t) < 75000) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    System.err.println("finished");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.err.println("writing id3v1.1");
                ID3 id3 = new ID3v1();
                id3.setTitle("Testtitel");
                id3.setArtist("Testartist");
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

    @Override
    public void saveToFile() throws IOException {

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
