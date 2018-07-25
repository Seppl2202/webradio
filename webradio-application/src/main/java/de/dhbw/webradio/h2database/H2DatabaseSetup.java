package de.dhbw.webradio.h2database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseSetup implements DatabaseSetup {
    private static final String DB_DRIVER = "org.h2.Driver";
    //H2 does not allow relative paths anymore!
    private static final String workingDirectory = new File("").getAbsolutePath();
    private static final String DB_CONNECTION = "jdbc:h2:file:" + workingDirectory.concat("\\webradio-application\\src\\main\\resources\\h2database\\test") + ";DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "webradio";
    private static final String DB_PASSWORD = "webradio";

    private static Object driverInstance = null;


    public Connection getConnection() throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, SQLException {

        Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        return conn;
    }
}
