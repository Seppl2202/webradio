package de.dhbw.webradio.h2database;


import org.h2.tools.Server;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * This class initializes the database structure and adds default stations
 */
public class InitializeH2Database {

    private  static DatabaseSetup databaseSetup = new H2DatabaseSetup();

    public static void initialiteDatabase() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Server server = Server.createTcpServer();
        server.start();
    }

}
