package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.enumerations.FileExtension;

import java.net.URL;

public class FileExtensionParser {
    /**
     *Parses a file extension
     * @param filenameUrl the url
     * @return the filename. if filename cannot be determined by file extension, Apache Tika parses by live detection
     */
    public FileExtension parseFileExtension(URL filenameUrl) {
        String filename = filenameUrl.toString();
        if (filename.endsWith(".mp3")) {
            return FileExtension.MP3;
        } else if (filename.endsWith(".m3u") || filename.endsWith(".m3u8")) {
            return FileExtension.M3U;
        } else if (filename.endsWith(".aac")) {
            return FileExtension.AAC;
        }
        URLTypeParser parser = new URLTypeParser();
        return parser.parseByContentDetection(filenameUrl);
    }
}
