package de.dhbw.webradio.test;

import de.dhbw.webradio.models.ScheduledRecord;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ScheduledRecordTest {

    ScheduledRecord r1 = new ScheduledRecord("Testtitel1", "Testinterpret1");
    ScheduledRecord r2= new ScheduledRecord("Testtitel2", "Testinterpret2");
    ScheduledRecord r3 = new ScheduledRecord("Testtitel1", "Testinterpret1");

    @Test
    public void getTitle() {
        assertEquals("Testtitel1", r1.getTitle());
        assertEquals("Testtitel2", r2.getTitle());
    }

    @Test
    public void getActor() {
        assertEquals("Testinterpret1", r1.getActor());
        assertEquals("Testinterpret2", r2.getActor());
    }

    @Test
    public void setTitle() {
        r1.setTitle("Test1");
        assertEquals("Test1", r1.getTitle());
    }

    public void setActor() {
        r2.setActor("Interpret2");
        assertEquals("Interpret2", r2.getActor());
    }

    @Test
    public void equals() {
        assertEquals(false, r1.equals(r2));
        r1.setTitle("Testtitel1");
        assertEquals(true, r3.equals(r1));
        r1.setTitle("testtitel1");
        r1.setActor("testinterpret1");
        assertEquals(true, r1.equals(r3));
    }
}
