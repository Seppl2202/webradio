package de.dhbw.webradio.test.id3;

import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1;
import de.dhbw.webradio.id3.ID3v1Builder;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ID3v1BuilderTest {

    @Test
    public void setAlbum() {
    }

    @Test
    public void setGenre() {
    }

    @Test
    public void setTrack() {
    }

    @Test
    public void setYear() {
    }

    @Test
    public void setComment() {
    }

    @Test
    public void build() {
        ID3 id3v1 = new ID3v1Builder("Testtitel", "Testinterpret")
                .setAlbum("Testalbum")
                .setComment("Testkommentar")
                .setGenre(1)
                .setTrack("2")
                .setYear("2018").build();

        assertEquals("Testtitel", id3v1.getTitle());
        assertEquals("Testinterpret", id3v1.getArtist());
        assertEquals("Testalbum", id3v1.getAlbum());
        assertEquals("Testkommentar", id3v1.getComment());
        assertEquals(1, id3v1.getGenre());
        assertEquals("2", id3v1.getTrack());
        assertEquals("2018", id3v1.getYear());
    }

    @Test
    public void buildWithMinimumInfo() {
        ID3 id3v1_2 = new ID3v1Builder("Testtitel", "Testinterpret").build();
        assertEquals("0", id3v1_2.getTrack());
        assertEquals("Kein Album verfügbar", id3v1_2.getAlbum());
        assertEquals("2018", id3v1_2.getYear());
        assertEquals("Recorded with WebradioPlayer", id3v1_2.getComment());
    }
}
