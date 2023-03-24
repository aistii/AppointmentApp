package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.helper.TimeChecks;
import lgarn67.appointmentapp.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Class for queries regarding the appointments table in the database.
 */
public class AppointmentQuery {

    /**
     * Runs a SELECT query on the appointments table in the database and then uses the data to populate the Observable List in the Working class.
     */
    public static void selectAllAppt() throws SQLException {
        String query = "SELECT * FROM appointments;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startLDT = rs.getTimestamp("Start").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            Customer customer = CustomerQuery.selectCustomer(custId);
            int userId = rs.getInt("User_ID");
            User user = UserQuery.userFetch(userId);
            int contId = rs.getInt("Contact_ID");
            assert contId != 0 : "Contact Not Found";
            Contact cont = ContactQuery.contactFetch(contId);

            Working.addAppointment(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }

    /**
     * Selects all appointments that have a start time later than the moment the user logged in.
     * This is used for checking if there are any appointments within the next 15 minutes of the time the user logs in.
     *
     * @param loginTime the login time of the user.
     * @return the result set of appointments occuring after user login.
     */
    public static ResultSet selectAllAppt(Instant loginTime) throws SQLException {
        String query = "SELECT * " +
                "FROM appointments " +
                "WHERE (Start >= ?) " +
                "ORDER BY Start;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setTimestamp(1, Timestamp.from(loginTime));
        return ps.executeQuery();
    }

    /**
     * Selects the specific appointment for the one selected. Used with the edit appointment controller.
     *
     * @param apptId the appointment id
     * @return the selected appointment
     */
    public static Appointment selectAppt(int apptId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE Appointment_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, apptId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("Appointment_ID");
        String title = rs.getString("Title");
        String desc = rs.getString("Description");
        String loc = rs.getString("Location");
        String type = rs.getString("Type");
        LocalDateTime startLDT = rs.getTimestamp("Start").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
        LocalDateTime endLDT = rs.getTimestamp("End").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
        int custId = rs.getInt("Customer_ID");
        Customer customer = CustomerQuery.selectCustomer(custId);
        int userId = rs.getInt("User_ID");
        User user = UserQuery.userFetch(userId);
        int contId = rs.getInt("Contact_ID");
        assert contId != 0 : "Contact Not Found";
        Contact cont = ContactQuery.contactFetch(contId);
        return (new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
    }

    /**
     * Runs a SELECT query on the appointments table in the database.
     * It only selects the ones of the current month.
     * This is then saved in the Observable List in the working class.
     */
    public static void selectMonthAppt() throws SQLException {

        String query = "SELECT * FROM appointments WHERE (MONTH(?) = MONTH(Start)) AND (YEAR(?) = YEAR(Start));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        String queryPara = (LocalDateTime.now().format(TimeChecks.dtf));
        ps.setString(1, queryPara);
        ps.setString(2, queryPara);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startLDT = rs.getTimestamp("Start").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            Customer customer = CustomerQuery.selectCustomer(custId);
            int userId = rs.getInt("User_ID");
            User user = UserQuery.userFetch(userId);
            int contId = rs.getInt("Contact_ID");
            Contact cont = ContactQuery.contactFetch(contId);
            Working.addApptMonth(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }

    /**
     * Runs a SELECT query on the appointments table in the database.
     * It only selects the ones of the current week.
     * This is then saved in the Observable List in the working class.
     */
    public static void selectWeekAppt() throws SQLException {

        String query = "SELECT * FROM appointments WHERE (WEEK(?) = WEEK(start)) AND (WEEK(?) = WEEK(start));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, (LocalDateTime.now()).format(TimeChecks.dtf));
        ps.setString(2, (LocalDateTime.now()).format(TimeChecks.dtf));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startLDT = rs.getTimestamp("Start").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            Customer customer = CustomerQuery.selectCustomer(custId);
            int userId = rs.getInt("User_ID");
            User user = UserQuery.userFetch(userId);
            int contId = rs.getInt("Contact_ID");
            Contact cont = ContactQuery.contactFetch(contId);
            Working.addApptWeek(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }

    /**
     * Selects all appointments that have a certain customer ID.
     * This is used when checking for appointment time overlaps in the add appointment scene.
     *
     * @param custId the customer id
     * @return the result set of appointments associated with the customer id
     */
    public static ResultSet selectCustAppts(int custId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE (Customer_ID = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        return ps.executeQuery();
    }

    /**
     * Selects all appointments that have a certain customer ID.
     * This is used when checking for appointment time overlaps in the edit appointment scene.
     * It excludes the appointment currently being edited to not include it in the result set for comparison.
     *
     * @param custId the customer id
     * @param apptId the appointment id of the appointment being edited
     * @return the result set of appointments associated with the customer id
     */
    public static ResultSet selectCustAppts(int custId, int apptId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE ((Customer_ID = ?) AND (Appointment_ID != ?));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.setInt(2, apptId);
        return ps.executeQuery();
    }

    /**
     * Inserts appointment into the database.
     *
     * @param title   the title of appointment
     * @param desc    the description of appointment
     * @param loc     the location of appointment
     * @param type    the type of appointment
     * @param startDT the start date-time of appointment
     * @param endDT   the end date-time of appointment
     * @param custId  the associated customer's id
     * @param userId  the associated user's id
     * @param contId  the associated contact's id
     */
    public static void insertAppointment(String title, String desc, String loc, String type, Instant startDT,
                                         Instant endDT, int custId, int userId, int contId) throws SQLException {
        String query = "INSERT INTO appointments " +
        " (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
        "Customer_ID, User_ID, Contact_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, loc);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.from(startDT));
        ps.setTimestamp(6, Timestamp.from(endDT));
        ps.setString(7, Working.getLoggedInName() );
        ps.setString(8, Working.getLoggedInName());
        ps.setInt(9, custId);
        ps.setInt(10, userId);
        ps.setInt(11, contId);
        ps.executeUpdate();
    }

    /**
     * Updates appointment in the database.
     *
     * @param apptId  the appointment id
     * @param title   the updated title of appointment
     * @param desc    the updated description of appointment
     * @param loc     the updated location of appointment
     * @param type    the updated type of appointment
     * @param startDt the updated start date-time of appointment
     * @param endDt   the updated end date-time of appointment
     * @param custId  the updated associated customer's id
     * @param userId  the updated associated user's id
     * @param contId  the updated associated contact's id
     */
    public static void updateAppointment(int apptId, String title, String desc, String loc, String type, Instant startDt,
                                         Instant endDt, int custId, int userId, int contId) throws SQLException {
        String query = "UPDATE appointments " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = NOW()," +
                "Last_Updated_By = ?," +
                "Customer_ID = ?," +
                "User_ID = ?," +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, loc);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.from(startDt));
        ps.setTimestamp(6, Timestamp.from(endDt));
        ps.setString(7, Working.getLoggedInName());
        ps.setInt(8, custId);
        ps.setInt(9, userId);
        ps.setInt(10, contId);
        ps.setInt(11, apptId);
        ps.executeUpdate();
    }

    /**
     * Deletes a single appointment from the database.
     *
     * @param apptId the appointment id
     */
    public static void deleteAppointment(int apptId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, apptId);
        ps.executeUpdate();
    }

    /**
     * Deletes the appointments associated with a certain customer from the database.
     * Invoked when a customer is deleted.
     *
     * @param custId the customer id
     */
    public static void delCustAppts(int custId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Customer_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.executeUpdate();
    }

}
