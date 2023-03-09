package lgarn67.appointmentapp.dao;

import javafx.collections.ObservableList;
import lgarn67.appointmentapp.Country;
import lgarn67.appointmentapp.Customer;
import lgarn67.appointmentapp.Division;
import lgarn67.appointmentapp.Working;

import javax.xml.transform.Result;
import java.security.cert.CertificateParsingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerQuery {
    //TODO
    // Add
    // Remove
    // Update
    // Read
    public static void selectAll() throws SQLException {
        // Selecting details from customres
        String query = "SELECT * FROM customers;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rsetC = ps.executeQuery();
        while(rsetC.next()) {
            int id = rsetC.getInt("Customer_ID");
            String cName = rsetC.getString("Customer_Name");
            String cAddress = rsetC.getString("Address");
            String cPostCode = rsetC.getString("Postal_Code");
            String cPhoneNum = rsetC.getString("Phone");
            int cDivId = rsetC.getInt("Division_ID");
            // Using the return value of findDivisionCountry(),
            // we can save the return value into a string array
            // and then use the String array when doing addCustomer().
            String[] ctryAndFLD = findDivisionCountry(cDivId);
            Working.addCustomer(new Customer(id, cName, cAddress, cPhoneNum, ctryAndFLD[0], ctryAndFLD[1], cPostCode));
        }
    }
    public static String[] findDivisionCountry (int divisionId) throws SQLException {
        String[] location = new String[2];
        String query = "SELECT countries.country, first_level_divisions.Division\n" +
                "FROM first_level_divisions\n" +
                "JOIN countries on countries.COUNTRY_ID = first_level_divisions.COUNTRY_ID\n" +
                "WHERE first_level_divisions.Division_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            location[0] =  rs.getString("country");
            location[1] = rs.getString("Division");
        }
        return location;
    }

    // The getCountries() acts in a similar manner to the selectAll() method.
    public static void getCountries() throws SQLException {
        Working.resetCountries();
        String query = "SELECT Country_ID, Country FROM countries;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            Working.addCountry(new Country(id, name));
        }
    }

    // This depends on a country ID to be passed in, most likely from what is selected in the Country combobox
    public static void getRelatedDivisions(int ctryId) throws SQLException {
        String query = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, ctryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            Working.addDivision(new Division(id, name));
        }
    }
}
