package de.dhbw.webradio.test.h2database;

import de.dhbw.webradio.h2database.H2DatabaseSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class H2DatabaseSetupTest {
    public H2DatabaseSetup databaseSetup;
    public Connection con;
    @Before
    public void setUp() {
        databaseSetup = new H2DatabaseSetup();
    }

    /**
     * checks if the connection to the database is succesfully established
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        con = databaseSetup.getConnection();
    }

    @After
    public void tearDown() throws SQLException {
        con.close();
    }
}
