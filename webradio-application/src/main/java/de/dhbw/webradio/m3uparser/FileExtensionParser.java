package de.dhbw.webradio.m3uparser;

import de.dhbw.webradio.enumerations.FileExtension;

public class FileExtensionParser {
    public FileExtension parseFileExtension(String filename) {
        if (filename.endsWith(".mp3")) {
            return FileExtension.MP3;
        } else if (filename.endsWith(".m3u") || filename.endsWith(".m3u8")) {
            return FileExtension.M3U;
        } else if (filename.endsWith(".aac") || (!filename.endsWith(".mp3") && filename.contains("aac"))) {
            return FileExtension.AAC;
        }
        return FileExtension.UNSUPPORTED_TYPE;
    }
}
