package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.model.Contact;
import lgarn67.appointmentapp.model.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for queries regarding the contacts table in the database.
 */
public class ContactQuery {
    /**
     * Finds the contact associated with an appointment on the appointment table views.
     *
     * @param contactId the contact id
     * @return the contact of the appointment
     */
    public static Contact contactFetch (int contactId) throws SQLException {
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

    /**
     * Runs a SELECT query on the contacts table in the database and then uses the data to populate the Observable List in the Working class.
     * It is used to populate any combo boxes that hold contacts in them.
     */
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
