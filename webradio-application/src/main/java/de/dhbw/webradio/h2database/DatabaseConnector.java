package de.dhbw.webradio.h2database;

import de.dhbw.webradio.models.Station;

import java.util.List;

public interface DatabaseConnector {

    public static DatabaseConnector getInstance() { return null;}

    public boolean updateStation(Station stationToChange, Station newStation);
    public boolean deleteStation(Station stationToDelete);
    public boolean addStation(Station stationToAdd);
    public List<Station> getStations();
}