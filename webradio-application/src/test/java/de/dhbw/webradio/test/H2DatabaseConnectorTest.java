package de.dhbw.webradio.test;

import de.dhbw.webradio.h2database.DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseConnector;
import de.dhbw.webradio.h2database.H2DatabaseSetup;
import de.dhbw.webradio.h2database.InitializeH2Database;
import de.dhbw.webradio.models.Station;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class H2DatabaseConnectorTest {
    private DatabaseConnector databaseConnector = H2DatabaseConnector.getInstance();


    @Test
    public void getInstance() {
        assertTrue(databaseConnector instanceof H2DatabaseConnector);
    }

    @Test
    public void updateStation() {
    }

    @Test
    public void deleteStation() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, MalformedURLException {
        InitializeH2Database.initialiteDatabase();
        Station station = new Station("Testsender", new URL("http://testurl.de/audio.mp3"));
        databaseConnector.deleteStation(station);
        List<Station> stations = databaseConnector.getStations();
        Station found = null;
        for (Station s: stations) {
            if(s.equals(station)) {
                found = s;
            }
        }
        assertEquals(null, found);
    }

    @Test
    public void addStation() throws MalformedURLException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        InitializeH2Database.initialiteDatabase();
        Station station = new Station("Testsender", new URL("http://testurl.de/audio.mp3"));
        databaseConnector.addStation(station);
        List<Station> stations = databaseConnector.getStations();
        Station found = null;
        for (Station s : stations
                ) {
            if (s.equals(station)) {
                found = s;
            }
        }
        assertEquals(station, found);
    }


    @Test
    public void getStations() {
    }
}
