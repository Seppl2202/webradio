package de.dhbw.webradio.h2database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseSetup {
    public Connection getConnection() throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException;
}
