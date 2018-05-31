package de.dhbw.webradio.id3;

import de.dhbw.webradio.utilities.StringUtilitie;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ID3v1 implements ID3 {
    public static final int TAG_LENGTH = 128;
    private static final String VERSION_0 = "0";
    private static final String VERSION_1 = "1";
    private static final String TAG = "TAG";
    private static final int TITLE_OFFSET = 3;
    private static final int TITLE_LENGTH = 30;
    private static final int ARTIST_OFFSET = 33;
    private static final int ARTIST_LENGTH = 30;
    private static final int ALBUM_OFFSET = 63;
    private static final int ALBUM_LENGTH = 30;
    private static final int YEAR_OFFSET = 93;
    private static final int YEAR_LENGTH = 4;
    private static final int COMMENT_OFFSET = 97;
    private static final int COMMENT_LENGTH_V1_0 = 30;
    private static final int COMMENT_LENGTH_V1_1 = 28;
    private static final int TRACK_MARKER_OFFSET = 125;
    private static final int TRACK_OFFSET = 126;
    private static final int GENRE_OFFSET = 127;

    private String track = null;
    private String artist = null;
    private String title = null;
    private String album = null;
    private String year = null;
    private int genre = -1;
    private String comment = null;
    private byte[] id3InfoToWrite;

    public ID3v1() {
        id3InfoToWrite = new byte[128];
    }

    public ID3v1(byte[] id3Info) {
        readId3Info(id3Info);
    }

    private void readId3Info(byte[] id3Info) {
        checkTag(id3Info);
        title = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, TITLE_OFFSET, TITLE_LENGTH));
        artist = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, ARTIST_OFFSET, ARTIST_LENGTH));
        album = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, ALBUM_OFFSET, ALBUM_LENGTH));
        year = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, YEAR_OFFSET, YEAR_LENGTH));
        genre = id3Info[GENRE_OFFSET] & 0xFF;
        if (genre == 0xFF) {
            genre = -1;
        }
        if (id3Info[TRACK_MARKER_OFFSET] != 0) {
            comment = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, COMMENT_OFFSET, COMMENT_LENGTH_V1_0));
        } else {
            comment = StringUtilitie.trimStringRightEnd(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(id3Info, COMMENT_OFFSET, COMMENT_LENGTH_V1_1));
            int trackInt = id3Info[TRACK_OFFSET];
            if (trackInt == 0) {
                track = "";
            } else {
                track = Integer.toString(trackInt);
            }
        }
    }

    public void writeId3Info() {
        Arrays.fill(id3InfoToWrite, (byte) 0);
        try {
            StringUtilitie.stringToByteArray(TAG, 0, 3, id3InfoToWrite, 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writeField(id3InfoToWrite, title, TITLE_LENGTH, TITLE_OFFSET);
        writeField(id3InfoToWrite, artist, ARTIST_LENGTH, ARTIST_OFFSET);
        writeField(id3InfoToWrite, album, ALBUM_LENGTH, ALBUM_OFFSET);
        if (genre < 128) {
            id3InfoToWrite[GENRE_OFFSET] = (byte) genre;
        } else {
            id3InfoToWrite[GENRE_OFFSET] = (byte) (genre - 256);
        }

        if (track == null) {
            writeField(id3InfoToWrite, comment, COMMENT_LENGTH_V1_0, COMMENT_OFFSET);
        } else {
            writeField(id3InfoToWrite, comment, COMMENT_LENGTH_V1_1, COMMENT_OFFSET);
            String trackTemp = numbersOnlyString(track);
            if (trackTemp.length() > 0) {
                int trackInt = Integer.parseInt(trackTemp);
                if (trackInt < 128) {
                    id3InfoToWrite[TRACK_OFFSET] = (byte) trackInt;
                } else {
                    id3InfoToWrite[TRACK_OFFSET] = (byte) (trackInt - 256);
                }
            }
        }
    }

    private void checkTag(byte[] byteTag) {
        if (byteTag.length != TAG_LENGTH) {
            throw new IllegalArgumentException("Tag length did not match ID3v1 specification of 128. Instead it was: " + byteTag.length);
        }
        if (!TAG.equals(StringUtilitie.byteArrayToStringIgnoreEncodingIssue(byteTag, 0, TAG.length()))) {
            throw new IllegalArgumentException("No ID3v1 tag found!");
        }
    }

    private void writeField(byte[] id3ToWrite, String value, int maxLength, int offset) {
        if (value != null) {
            try {
                StringUtilitie.stringToByteArray(value, 0, Math.min(value.length(), maxLength), id3ToWrite, offset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private String numbersOnlyString(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                builder.append(c);
            } else {
                break;
            }
        }
        return builder.toString();
    }

    public String getGenreAsString() {
        try {
            return ID3v1Genres.ID3v1Genres[genre];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ID3v1{" +
                "track='" + track + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", genre=" + genre +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public void writeId3ToFile(File f) {
        try {
            FileUtils.writeByteArrayToFile(f, id3InfoToWrite, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
