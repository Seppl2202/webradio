package de.dhbw.webradio.test.h2database;

import de.dhbw.webradio.h2database.DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseSetup;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class H2DatabaseConnectorTest {
    private DatabaseConnector databaseConnector = H2DatabaseConnector.getInstance();
    private List<Station> addedStations = new ArrayList();

    @Before
    public void setUp() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        InitializeH2Database.initialiteDatabase();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @After
    public void tearDown() {
        addedStations.forEach(s -> databaseConnector.deleteStation(s));
    }
}
