package de.dhbw.webradio.id3;

import java.util.Optional;

public  class ID3v1Builder {
    private final String title, artist;
    private String track = null;
    private String album = null;
    private String year = null;
    private int genre = -1;
    private String comment = null;

    public ID3v1Builder(String title, String artist) {
        this.artist = artist;
        this.title = title;
    }

    public ID3v1Builder setAlbum(String album) {
        this.album = album;
        return this;
    }

    public ID3v1Builder setGenre(int genre) {
        this.genre = genre;
        return this;
    }

    public ID3v1Builder setTrack(String track) {
        this.track = track;
        return this;
    }

    public ID3v1Builder setYear(String year) {
        this.year = year;
        return this;
    }

    public ID3v1Builder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ID3v1 build() {
        Optional<String> albumOptional = Optional.ofNullable(album);
        Optional<String> trackOptional = Optional.ofNullable(track);
        Optional<Integer> genreOptional = Optional.ofNullable(genre);
        Optional<String> commentOptional = Optional.ofNullable(comment);
        Optional<String> yearOptional = Optional.ofNullable(year);
        return new ID3v1(title, artist, trackOptional.orElse("0"), albumOptional.orElse("Kein Album verfügbar"), yearOptional.orElse("2018"), genreOptional.orElse(1), commentOptional.orElse("Recorded with WebradioPlayer"));
    }
}
