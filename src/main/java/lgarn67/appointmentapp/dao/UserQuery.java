package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.User;
import lgarn67.appointmentapp.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery {

    // select all users is for populating the combo box
    public static void selectAllUsers() throws SQLException {
        String query = "SELECT User_ID, User_Name FROM users;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            Working.addUser(new User(id, userName));
        }
    }

    public static User userFetch (int userId) throws SQLException {
        String query = "SELECT User_ID, User_Name FROM users WHERE User_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("User_ID");
        String uName = rs.getString("User_Name");
        return (new User(id, uName));
    }
}
