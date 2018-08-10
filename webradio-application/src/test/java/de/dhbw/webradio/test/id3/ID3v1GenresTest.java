package de.dhbw.webradio.test.id3;


import de.dhbw.webradio.id3.ID3;
import de.dhbw.webradio.id3.ID3v1Builder;
import de.dhbw.webradio.id3.ID3v1Genres;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ID3v1GenresTest {

    @Test
    public void testGenre() {
        ID3 id3v1 = new ID3v1Builder("Test", "Test").setGenre(2).build();
        assertEquals(2, id3v1.getGenre());
        assertEquals("Country", ID3v1Genres.ID3v1Genres[id3v1.getGenre()]);

    }

}
