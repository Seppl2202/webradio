package de.dhbw.webradio.h2database;


import javax.xml.transform.Result;
import java.sql.*;

/**
 * This class initializes the database structure and adds default stations
 */
public class InitializeH2Database {

    public static void initialiteDatabase() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection con = DatabaseSetup.getConnection();
        Statement s = con.createStatement();
        try {
            s.execute("CREATE TABLE IF NOT EXISTS station (name VARCHAR(50), url VARCHAR(150))");
        } finally {
            s.close();
            con.close();
        }
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
        Connection con = DatabaseSetup.getConnection();
        Statement s = con.createStatement();
        try {
            ResultSet r = s.executeQuery("SELECT * FROM station");
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
        PreparedStatement insertPrepared = DatabaseSetup.getConnection().prepareStatement(insert);
        insertPrepared.setString(1, "Testsender");
        insertPrepared.setString(2, "http://testurl.de/sadsd.mp3");
        insertPrepared.executeUpdate();
        insertPrepared.close();
    }
}
