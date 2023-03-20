package lgarn67.appointmentapp.dao;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginConnect {
    //TODO
    // This part will verify the password and such for login.


    public static boolean checkLogin(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE (User_Name = ? AND Password = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int counting = rs.getInt("COUNT(*)");
            if (counting != 1) {
                return false;
            } else {
                return true;
            }
    }

    // these fetch is to keep track for later~~~
    public static int fetchId (String username, String password) throws SQLException {
        String query = "SELECT User_ID FROM users WHERE (User_Name = ? AND Password = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int userId = rs.getInt("User_ID");
        //System.out.println(userId);
        return userId;
    }

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
