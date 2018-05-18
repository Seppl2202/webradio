package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.enumerations.FileExtension;

public class FileExtensionParser {
    public static FileExtension parseFileExtension(String filename) {
        if (filename.endsWith(".mp3")) {
            return FileExtension.MP3;
        } else if (filename.endsWith(".m3u")) {
            return FileExtension.M3U;
        }
        return FileExtension.UNSUPPORTED_TYPE;
    }
}
