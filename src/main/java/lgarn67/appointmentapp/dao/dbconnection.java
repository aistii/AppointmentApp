package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.controller.alert_ctlr;

import java.sql.*;

/**
 * Used to connect to the database.
 */
public class dbconnection {
    /**
     * The protocol name used to connect to database.
     */
    private static final String protocol = "jdbc";
    /**
     * The vendor of database's name.
     */
    private static final String vendor = ":mysql:";
    /**
     * The location of the database.
     */
    private static final String location = "//127.0.0.1:3306/";
    /**
     * The name of the database being connected to.
     */
    private static final String databaseName = "client_schedule";
    /**
     * The URL to be used in the getConnection method.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    /**
     * The reference to the driver for the database.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * The username to access the database.
     */
    private static final String userName = "sqlUser";
    /**
     * The password to access the database.
     */
    private static final String password = "Passw0rd!";
    /**
     * The connection to the database.
     */
    public static Connection connection;

    /**
     * Attempts to connect to the database with the given URL, username, and password.
     * If not successful, it will display an error message.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            connection = (Connection) DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            alert_ctlr.errorConnecting();
        }
    }

    /**
     * Closes the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
        }
    }
}
