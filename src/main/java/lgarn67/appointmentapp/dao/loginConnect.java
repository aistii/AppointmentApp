package lgarn67.appointmentapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for checking the username and password combination on the login screen against the values in the database.
 */
public class loginConnect {

    /**
     * Checks to make sure that the provided username and password from the login screen only have one row in the users table in the database.
     * That should be the case, since the username is unique.
     *
     * @param username the attempted username
     * @param password the attempted password
     * @return if there is only one matching row with the provided username and password.
     */
    public static boolean checkLogin(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE (User_Name = ? AND Password = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int counting = rs.getInt("COUNT(*)");
        return counting == 1;
    }

    /**
     * Retrieves the ID number of the user that has logged in.
     *
     * @param username the username
     * @param password the password
     * @return the ID number of the user
     */
    public static int fetchId (String username, String password) throws SQLException {
        String query = "SELECT User_ID FROM users WHERE (User_Name = ? AND Password = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("User_ID");
    }

    /**
     * Retrieves the username of the user that has logged in.
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    public static String fetchUsername (String username, String password) throws SQLException  {
        String query = "SELECT User_Name FROM users WHERE (User_Name = ? AND Password = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("User_Name");
    }
}
