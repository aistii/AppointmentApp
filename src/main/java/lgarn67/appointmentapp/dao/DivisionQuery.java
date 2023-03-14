package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.Division;
import lgarn67.appointmentapp.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionQuery {
    // This depends on a country ID to be passed in, most likely from what is selected in the Country combobox
    public static void getRelatedDivisions(int ctryId) throws SQLException {

        try {
            String query = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?;";
            PreparedStatement ps = dbconnection.connection.prepareStatement(query);
            ps.setInt(1, ctryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                Working.addDivision(new Division(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
