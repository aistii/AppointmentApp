package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.Country;
import lgarn67.appointmentapp.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryQuery {

    // The getCountries() acts in a similar manner to the selectAll() method.
    public static void getCountries() throws SQLException {
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
            throwables.printStackTrace();
        }
    }
}
