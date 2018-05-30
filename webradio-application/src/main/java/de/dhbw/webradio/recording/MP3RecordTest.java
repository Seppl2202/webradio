package de.dhbw.webradio.recording;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MP3RecordTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://swr-swr1-bw.cast.addradio.de/swr/swr1/bw/mp3/128/stream.mp3");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStream in = con.getInputStream();
        OutputStream out = new FileOutputStream(new File("C://repository/recmp.mp3"));
        byte[] buffer = new byte[4096];
        int len;
        long t = System.currentTimeMillis();
        System.err.println("entering while");
        while((len = in.read(buffer)) > 0 && System.currentTimeMillis() - t <= 5000) {
            out.write(buffer, 0 , len);
        }
        System.err.println("finished");
        out.close();
    }
}
