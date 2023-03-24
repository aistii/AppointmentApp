package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.model.Country;
import lgarn67.appointmentapp.model.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for queries regarding the countries table in the database.
 */
public class CountryQuery {

    /**
     * Selects all of the countries present in the countries table and adds them to Observable List in the Working class.
     */
    public static void getAllCountries() throws SQLException {
        try {
            Working.resetCountries();
            String query = "SELECT Country_ID, Country FROM countries;";
            PreparedStatement ps = dbconnection.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Working.addCountry(new Country(id, name));
            }
        } catch (SQLException throwables) {
        }
    }

    /**
     * Gets the country that the customer lives in via the customer's division ID.
     *
     * @param divId the customer's first-level division id
     * @return the customer's country
     */
    public static Country getCustCtry(int divId) throws SQLException {
        String query = "SELECT countries.Country_ID, countries.Country " +
                "FROM first_level_divisions " +
                "JOIN countries ON countries.Country_ID = first_level_divisions.COUNTRY_ID " +
                "WHERE first_level_divisions.Division_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String cname = rs.getString("Country");
        int cId = rs.getInt("country_id");
        return (new Country(cId, cname));
    }
}
