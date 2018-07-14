package de.dhbw.webradio.h2database;

import de.dhbw.webradio.models.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseConnector implements DatabaseConnector {

    private final String INSERT_STATEMENT = "INSERT INTO station" + "(name, url) values(?,?)";
    private final String UPDATE_STATEMENT = "UPDATE station SET name=?, url=? WHERE (name=? AND url=?)";
    private final String DELETE_STATEMENT = "DELETE FROM station WHERE (name=? AND url=?)";

    private static DatabaseConnector h2databaDatabaseConnector = new H2DatabaseConnector();

    private H2DatabaseConnector() {

    }

    @Override
    public DatabaseConnector getInstance() {
        return h2databaDatabaseConnector;
    }

    @Override
    public boolean updateStation(Station stationToChange, Station newStation) {
        Connection con = null;
        PreparedStatement s = null;
        try {
            con = DatabaseSetup.getConnection();
            s = con.prepareStatement(UPDATE_STATEMENT);
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
            con = DatabaseSetup.getConnection();
            s = con.prepareStatement(DELETE_STATEMENT);
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
        try {
            con = DatabaseSetup.getConnection();
            s = con.prepareStatement(INSERT_STATEMENT);
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

    @Override
    public List<Station> getStations() {
        List<Station> stationsList = new ArrayList<>();

        return stationsList;
    }
}
