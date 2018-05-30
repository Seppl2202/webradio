package de.dhbw.webradio.enumerations;

public enum FileExtension {
    MP3("mp3"), M3U("m3u"), AAC("aac"), PLS("pls"), UNSUPPORTED_TYPE("unsupported");

    private String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}