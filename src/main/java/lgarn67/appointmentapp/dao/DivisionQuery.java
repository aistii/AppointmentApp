package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.model.Division;
import lgarn67.appointmentapp.model.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for queries regarding the first-level divisions table in the database.
 */
public class DivisionQuery {
    /**
     * Gets all of the first-level divisions that have the country ID passed in.
     * It then populates the Observable List in the Working class.
     *
     * @param ctryId the country ID
     */
    public static void getAllRelatedDivisions(int ctryId) throws SQLException {
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
        }
    }

    /**
     * Gets the division id and name from the database.
     * This is so the name of the division may be saved into a Division object and can be displayed in the UI.
     *
     * @param divId the first-level division id
     * @return the customer's first-level division
     */
    public static Division getCustDiv (int divId) throws SQLException {
        String query = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int dId = rs.getInt("Division_ID");
        String dName = rs.getString("Division");
        return (new Division(dId, dName));
    }
}
