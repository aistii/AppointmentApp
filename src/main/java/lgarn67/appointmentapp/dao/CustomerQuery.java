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

    /*
        === SELECTION SECTION
     */
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
            //String[] ctryAndFLD = findDivisionCountry(cDivId);
            Country cCountry = CountryQuery.getCustCtry(cDivId);
            Division cDiv = DivisionQuery.getCustDiv(cDivId);

            // index 0 would be the country, index 1 is the division
            Working.addCustomer(new Customer(id, cName, cAddress, cPhoneNum, cCountry, cDiv, cPostCode));
        }
    }

    public static Customer selectCustomer(int custId) throws SQLException{
        String query = "SELECT Customer_Name, Address, Postal_Code, Phone, Division_ID " +
                "FROM customers " +
                "WHERE Customer_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String cName = rs.getString("Customer_Name");
        String cAddr = rs.getString("Address");
        String cPost = rs.getString("Postal_Code");
        String cPhone = rs.getString("Phone");
        int divId = rs.getInt("Division_ID");
        Country cCountry = CountryQuery.getCustCtry(divId);
        Division cDiv = DivisionQuery.getCustDiv(divId);
        return (new Customer(custId, cName, cAddr, cPhone, cCountry, cDiv, cPost));

    }
    // this join statement i feel should stay in customer...
    /*public static String[] findDivisionCountry (int divisionId) throws SQLException {
        String[] location = new String[0];
        try {
            location = new String[2];
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return location;
    }*/
    /*
        === ADD A CUSTOMER SECTION ===
        * It will have many parameters uhoh
     */

    public static void insertCustomer(String name, String address, String postal, String phone, int fldId) throws SQLException {
        String query = "INSERT INTO customers " +
        "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
        "VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setString(5, Working.getLoggedInName());
        ps.setString(6, Working.getLoggedInName());
        ps.setInt(7, fldId);
        ps.executeUpdate();
    }
    public static void updateCustomer(int custId, String cName, String cAddr, String cPost, String cPhone, int fldId) throws SQLException {
        String query = "UPDATE customers " +
                "SET Customer_Name = ?," + //1
                "Address = ?," + //2
                "Postal_Code = ?," + //3
                "Phone = ?," + //4
                "Last_Update = NOW()," +
                "Last_Updated_By = ?," + //5
                "Division_ID = ? " +//6
                "WHERE Customer_ID = ?"; //7
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, cName);
        ps.setString(2, cAddr);
        ps.setString(3, cPost);
        ps.setString(4, cPhone);
        ps.setString(5, Working.getLoggedInName());
        ps.setInt(6, fldId);
        ps.setInt(7, custId);
        ps.executeUpdate();
    }

    public static void deleteCustomer(int custId) throws SQLException {
        AppointmentQuery.delCustAppts(custId);
        String query = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.executeUpdate();
    }
}
