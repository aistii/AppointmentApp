package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.model.Country;
import lgarn67.appointmentapp.model.Customer;
import lgarn67.appointmentapp.model.Division;
import lgarn67.appointmentapp.model.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for queries regarding the customers table in the database.
 */
public class CustomerQuery {
    /**
     * Selects all of the customers from the database and then adds them to the Observable List in the working class.
     */
    public static void selectAll() throws SQLException {
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
            Country cCountry = CountryQuery.getCustCtry(cDivId);
            Division cDiv = DivisionQuery.getCustDiv(cDivId);
            Working.addCustomer(new Customer(id, cName, cAddress, cPhoneNum, cCountry, cDiv, cPostCode));
        }
    }

    /**
     * Selects the customer from the row selected in the customer view TableView.
     * Used to retrieve values to load into the edit customer form.
     *
     * @param custId the customer's id
     * @return the customer
     */
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
    /**
     * Inserts a customer into the database.
     *
     * @param name    the customer's name
     * @param address the customer's address
     * @param postal  the customer's postal
     * @param phone   the customer's phone
     * @param fldId   the customer's first-level division id
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

    /**
     * Updates a customer in the database.
     *
     * @param custId the customer's id
     * @param cName  the customer's updated name
     * @param cAddr  the customer's updated addr
     * @param cPost  the customer's updated post
     * @param cPhone the customer's updated phone
     * @param fldId  the customer's first-level division id
     */
    public static void updateCustomer(int custId, String cName, String cAddr, String cPost, String cPhone, int fldId) throws SQLException {
        String query = "UPDATE customers " +
                "SET Customer_Name = ?," +
                "Address = ?," +
                "Postal_Code = ?," +
                "Phone = ?," +
                "Last_Update = NOW()," +
                "Last_Updated_By = ?," +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?";
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

    /**
     * Deletes a customer from the database; it will first delete any appointments associated with the customer.
     *
     * @param custId the customer's id
     */
    public static void deleteCustomer(int custId) throws SQLException {
        AppointmentQuery.delCustAppts(custId);
        String query = "DELETE FROM customers WHERE Customer_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.executeUpdate();
    }
}
