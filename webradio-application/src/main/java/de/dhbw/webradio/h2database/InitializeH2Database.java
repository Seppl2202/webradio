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


    /**
     *
     * test method
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void getData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection con = databaseSetup.getConnection();
        Statement s = con.createStatement();
        try {
            ResultSet r = s.executeQuery("SELECT * FROM STATION");
            while(r.next()) {
                System.err.println("name: " + r.getString("name") + "url: " + r.getString("url"));
            }
        } finally {
            s.close();
            con.close();
        }
    }

    /**
     * test method
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void insert() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String insert = "INSERT INTO station" + "(name, url) values(?,?)";
        PreparedStatement insertPrepared = databaseSetup.getConnection().prepareStatement(insert);
        insertPrepared.setString(1, "Testsender");
        insertPrepared.setString(2, "http://testurl.de/sadsd.mp3");
        insertPrepared.executeUpdate();
        insertPrepared.close();
    }
}
