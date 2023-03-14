package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.Contact;
import lgarn67.appointmentapp.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactQuery {
    // contact fetch returns the contact associated with the appointment on the appointment views.
    public static Contact contactFetch (int contactId) throws SQLException {
        Contact newCon;
        String query = "SELECT Contact_ID, Contact_Name FROM contacts WHERE (Contact_ID = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1,contactId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer contId = rs.getInt("Contact_ID");
        String contName = rs.getString("Contact_Name");
        assert contName != null;
        return new Contact(contId, contName);
    }

    // this one selects all contacts for the combobox
    public static void selectAllContacts() throws SQLException {
        String query = "SELECT Contact_ID, Contact_Name FROM contacts;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String contName =  rs.getString("Contact_Name");
            Working.addContact(new Contact(id, contName));
        }
    }
}
