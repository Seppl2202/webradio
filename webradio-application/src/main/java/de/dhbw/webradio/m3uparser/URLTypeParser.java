package de.dhbw.webradio;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLTypeParser {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://streams.bigfm.de/bigfm-charts-128-aac?usid=0-0-H-A-D-30");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        InputStream in = con.getInputStream();
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser autoDetectParser = new AutoDetectParser();
        AudioParser audioParser = new AudioParser();
        org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
        try {
            audioParser.parse(in, handler, metadata);
            System.err.println(metadata.toString());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

}
