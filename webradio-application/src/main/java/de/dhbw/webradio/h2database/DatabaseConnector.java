package de.dhbw.webradio.h2database;

import de.dhbw.webradio.models.Station;

import java.util.List;

public interface DatabaseConnector {

    public DatabaseConnector getInstance();

    public boolean updateStation(Station stationToChange, Station newStation);
    public boolean deleteStation(Station stationToDelete);
    public boolean addStation(Station stationToAdd);
    public List<Station> getStations();
}
