package de.dhbw.webradio.recording;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AACRecorderTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://streams.bigfm.de/bigfm-deutschland-128-aac?usid=0-0-H-A-D-30");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            File f = new File("C://repository/recaac.aac");
            OutputStream out = new FileOutputStream(f);
            byte[] buffer = new byte[4096];
            int len;
            long t = System.currentTimeMillis();
            System.err.println("entering while");
            while ((len = in.read(buffer)) > 0 && (System.currentTimeMillis() - t) < 30000) {
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
    }
}
