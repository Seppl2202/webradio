package de.dhbw.webradio.h2database;

import de.dhbw.webradio.models.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseConnector implements DatabaseConnector {

    private final String INSERT_STATION_STATEMENT = "INSERT INTO STATION" + "(name, url) values(?,?)";
    private final String UPDATE_STATION_STATEMENT = "UPDATE STATION SET name=?, url=? WHERE (name=? AND url=?)";
    private final String DELETE_STATION_STATEMENT = "DELETE FROM STATION WHERE (name=? AND url=?)";
    private final String INSERT_RECORD_STATEMENT = "INSERT INTO RECORD" + "(name, url) values(?,?)";
    private final String UPDATE_RECORD_STATEMENT = "UPDATE RECORD SET name=?, url=? WHERE (artist=? AND name=?)";
    private final String DELETE_RECORD_STATEMENT = "DELETE RECORD STATION WHERE (artist=? AND title=?)";
    private DatabaseSetup databaseSetup = new H2DatabaseSetup();
    private static DatabaseConnector h2databaDatabaseConnector = new H2DatabaseConnector();

    private H2DatabaseConnector() {

    }

    public static DatabaseConnector getInstance() {
        return h2databaDatabaseConnector;
    }

    @Override
    public boolean updateStation(Station stationToChange, Station newStation) {
        Connection con = null;
        PreparedStatement s = null;
        try {
            con = databaseSetup.getConnection();
            s = con.prepareStatement(UPDATE_STATION_STATEMENT);
            s.setString(1, newStation.getName());
            s.setString(2, newStation.getStationURL().toString());
            s.setString(3, stationToChange.getName());
            s.setString(4, stationToChange.getStationURL().toString());
            s.executeUpdate();
            s.close();

        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean deleteStation(Station stationToDelete) {
        Connection con = null;
        PreparedStatement s = null;
        try {
            con = databaseSetup.getConnection();
            s = con.prepareStatement(DELETE_STATION_STATEMENT);
            s.setString(1, stationToDelete.getName());
            s.setString(2, stationToDelete.getStationURL().toString());
            s.executeUpdate();
            s.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean addStation(Station stationToAdd) {
        Connection con = null;
        PreparedStatement s = null;
        if(stationToAdd.getName().equals("")){
            throw new IllegalArgumentException("Station did not contain a valid name");
        }
        try {
            con = databaseSetup.getConnection();
            s = con.prepareStatement(INSERT_STATION_STATEMENT);
            s.setString(1, stationToAdd.getName());
            s.setString(2, stationToAdd.getStationURL().toString());
            s.executeUpdate();
            s.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @return all Stations saved in database
     */
    @Override
    public List<Station> getStations() {
        List<Station> stationsList = new ArrayList<>();
        try {
            Connection con = databaseSetup.getConnection();
            PreparedStatement s = con.prepareStatement("SELECT * FROM station");
            ResultSet r = s.executeQuery();
            while (r.next()) {
                stationsList.add(new Station(r.getString("name"), new URL(r.getString("url"))));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return stationsList;
    }
}
