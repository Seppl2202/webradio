package de.dhbw.webradio.test.h2database;

import de.dhbw.webradio.h2database.DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseConnector;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.models.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class H2DatabaseConnectorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private DatabaseConnector databaseConnector = H2DatabaseConnector.getInstance();
    private List<Station> addedStations = new ArrayList();
    private List<ScheduledRecord> scheduledRecords = new ArrayList<>();

    @Before
    public void setUp() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        InitializeH2Database.initialiteDatabase();
    }

    @Test
    public void getInstance() {
        assertTrue(databaseConnector instanceof H2DatabaseConnector);
    }


    @Test
    public void addStation() throws MalformedURLException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Station station = new Station("Testsender", new URL("http://testurl.de/audio.mp3"));
        databaseConnector.addStation(station);
        addedStations.add(station);
        List<Station> stations = databaseConnector.getStations();
        Station found = null;
        for (Station s : stations
                ) {
            if (s.equals(station)) {
                found = s;
            }
        }
        assertEquals(station, found);

        Station station2 = new Station("", new URL("http://www.test.de"));
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Station did not contain a valid name");
        databaseConnector.addStation(station2);
    }

    @Test
    public void deleteStation() throws MalformedURLException {

        Station station = new Station("Testsender", new URL("http://testurl.de/audio.mp3"));
        databaseConnector.deleteStation(station);
        List<Station> stations = databaseConnector.getStations();
        Station found = null;
        for (Station s : stations) {
            if (s.equals(station)) {
                found = s;
            }
        }
        assertEquals(null, found);
    }


    @Test
    public void getStations() throws MalformedURLException {
        Station s = new Station("Test", new URL("http://www,test.de"));
        databaseConnector.addStation(s);
        List<Station> stations = databaseConnector.getStations();
        assertTrue(stations.size() >= 1);
        assertTrue(stations.contains(s));
        databaseConnector.deleteStation(s);
        stations = databaseConnector.getStations();
        assertFalse(stations.contains(s));
    }

    @Test
    public void updateStation() throws MalformedURLException {
        Station oldStation = new Station("oldStation", new URL("http://www.old.de"));
        Station newStation = new Station("newStation", new URL("http://new.de"));
        databaseConnector.addStation(oldStation);
        databaseConnector.updateStation(oldStation, newStation);
        expectedException.expect(IndexOutOfBoundsException.class);
        Station oldStationFromDatabase = databaseConnector.getStations().get(databaseConnector.getStations().indexOf(oldStation));
        Station newStationFromDatabase = databaseConnector.getStations().get(databaseConnector.getStations().indexOf(newStation));
        assertEquals(newStation, newStationFromDatabase);
        addedStations.add(new Station("newStation", new URL("http://new.de")));
    }

    @Test
    public void addScheduledRecord() {
        ScheduledRecord r1 = new ScheduledRecord("Testtitel", "Testinterpret");
        databaseConnector.addScheduledRecord(r1);
        scheduledRecords.add(r1);
        List<ScheduledRecord> recordList = databaseConnector.getRecords();
        assertTrue(recordList.contains(r1));
    }

    @Test
    public void updateScheduledRecord() {
        ScheduledRecord oldRecord = new ScheduledRecord("TestAlt", "TestInterpretAlt");
        ScheduledRecord newRecord = new ScheduledRecord("TestNeu", "TestInterpretNeu");
        databaseConnector.addScheduledRecord(oldRecord);
        databaseConnector.updateScheduledRecord(oldRecord, newRecord);
        scheduledRecords.add(oldRecord);
        scheduledRecords.add(newRecord);
        assertTrue(databaseConnector.getRecords().contains(newRecord));
        assertTrue(databaseConnector.getRecords().get(databaseConnector.getRecords().indexOf(newRecord)).equals(newRecord));
        assertTrue(! databaseConnector.getRecords().contains(oldRecord));
    }

    @Test
    public void deleteRecord() {
        ScheduledRecord r = new ScheduledRecord("Testtitel", "Testinterpret");
        databaseConnector.addScheduledRecord(r);
        databaseConnector.deleteScheduledRecord(r);
        assertTrue(! databaseConnector.getRecords().contains(r));
    }

    @After
    public void tearDown() {
        addedStations.forEach(s -> databaseConnector.deleteStation(s));
        scheduledRecords.forEach(r -> databaseConnector.deleteScheduledRecord(r));
    }
}
