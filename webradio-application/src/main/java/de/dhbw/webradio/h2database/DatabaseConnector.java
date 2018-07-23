package de.dhbw.webradio.h2database;

import de.dhbw.webradio.models.ScheduledRecord;
import de.dhbw.webradio.models.Station;

import java.util.List;

public interface DatabaseConnector {

    public static DatabaseConnector getInstance() { return null;}

    public boolean updateStation(Station stationToChange, Station newStation);
    public boolean deleteStation(Station stationToDelete);
    public boolean addStation(Station stationToAdd);
    public boolean addScheduledRecord(ScheduledRecord recordToAdd);
    public boolean updateScheduledRecord(ScheduledRecord recordToChange, ScheduledRecord newRecord);
    public boolean deleteScheduledRecord(ScheduledRecord recordToDelete);
    public List<Station> getStations();
    public List<ScheduledRecord> getRecords();
}
