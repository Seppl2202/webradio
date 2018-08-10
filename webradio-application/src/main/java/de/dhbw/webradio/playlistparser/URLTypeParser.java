package de.dhbw.webradio.playlistparser;

import de.dhbw.webradio.enumerations.FileExtension;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.audio.AudioParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This class parses the mediatype directly from the URL input stream
 */
public class URLTypeParser {


    /** This class uses Apache Tika to parse an URL using her content
     *
     * @param url the webstream url
     * @return the detected file encoding: MP3, AAC or unsupported
     */

    public FileExtension parseByContentDetection(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            BodyContentHandler handler = new BodyContentHandler();
            AudioParser parser = new AudioParser();
            Metadata metadata = new Metadata();
            //AudioParser.parse is deprecated, but the only way to parse detailed audio types instead of MIME-Type audio
            parser.parse(in, handler, metadata);
            return parseMediaType(metadata);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return FileExtension.UNSUPPORTED_TYPE;
    }

    private FileExtension parseMediaType(Metadata metadata) {
        String parsedMediaType = metadata.get("encoding");
        if (parsedMediaType.equalsIgnoreCase("aac")) {
            return FileExtension.AAC;
        } else if (parsedMediaType.equalsIgnoreCase("mpeg1l3")) {
            return FileExtension.MP3;
        }
        return FileExtension.UNSUPPORTED_TYPE;
    }

}
