package de.dhbw.webradio.h2database;


import org.h2.tools.Server;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class initializes the database structure and adds default stations
 */
public class InitializeH2Database {

    private static DatabaseSetup databaseSetup = new H2DatabaseSetup();

    public static void initialiteDatabase() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Server server = Server.createTcpServer();
        server.start();
        createTable();
    }

    private static void createTable() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PreparedStatement createRecordsTable = databaseSetup.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS RECORD (artist VARCHAR (100), title VARCHAR (100))"
        );
        PreparedStatement createStationsTable = databaseSetup.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS STATION (name VARCHAR (100), url VARCHAR (150))"
        );

        createRecordsTable.executeUpdate();
        createStationsTable.executeUpdate();
        createRecordsTable.close();
        createStationsTable.close();
    }

}
