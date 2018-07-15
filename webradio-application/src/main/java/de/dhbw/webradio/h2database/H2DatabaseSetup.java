package de.dhbw.webradio.h2database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseSetup implements DatabaseSetup{
    private static final String DB_DRIVER ="org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "webradio";
    private static final String DB_PASSWORD = "webradio";

    private static Object driverInstance = null;


    public Connection getConnection() throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {

        Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        return conn;
    }
}
