package de.dhbw.webradio.test.id3;

import de.dhbw.webradio.id3.ID3v1;
import de.dhbw.webradio.id3.ID3v1Builder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ID3v1Test {


    @Test
    public void writeId3Info() {
    }

    @Test
    public void writeId3ToFile() {
    }

    /**
     * Tests correct writing of id3v1 byte[] and correct reading of id3v1 tags
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void readId3Info() throws NoSuchFieldException, IllegalAccessException {
        ID3v1 id3 = new ID3v1Builder("Testtitel", "Testinterpret")
                .setAlbum("Testalbum")
                .setGenre(1)
                .setYear("2018")
                .setComment("Kein Kommentar")
                .setTrack("2").build();
        id3.writeId3Info();
        Field f = id3.getClass().getDeclaredField("id3InfoToWrite");
        f.setAccessible(true);
        byte[] id3Info = (byte[]) f.get(id3);
        ID3v1 id3Test = new ID3v1(id3Info);
        assertEquals("Testtitel", id3Test.getTitle());
        assertEquals("Kein Kommentar", id3Test.getComment());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /** Tests reading invalid byte arrays
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException no id3v1 tag should be found
     */
    @Test
    public void testNoID3TagException() throws NoSuchFieldException, IllegalAccessException {
        ID3v1 id3 = new ID3v1Builder("Testtitel", "Testinterpret")
                .setAlbum("Testalbum")
                .setGenre(1)
                .setYear("2018")
                .setComment("Kein Kommentar")
                .setTrack("2").build();
        Field f = id3.getClass().getDeclaredField("id3InfoToWrite");
        //at this point, the id3 tags are not written to the target array yet, so getting the target array returns no id3 tags
        f.setAccessible(true);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No ID3v1 tag found");
        byte[] id3Info = (byte[]) f.get(id3);
        ID3v1 id3Test = new ID3v1(id3Info);
        assertEquals("Testtitel", id3Test.getTitle());
        assertEquals("Kein Kommentar", id3Test.getComment());
    }
}
