package de.dhbw.webradio.test.h2database;


import de.dhbw.webradio.h2database.InitializeH2Database;
import org.junit.Test;

import java.sql.SQLException;

public class InitializeH2DatabaseTest {


    @Test
    public void initialiteDatabase() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        InitializeH2Database.initialiteDatabase();
    }
}
