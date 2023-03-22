package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.helper.TimeChecks;
import lgarn67.appointmentapp.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentQuery {

    /*
        === Selection Queries ===
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
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object

            Working.addAppointment(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }

    public static ResultSet selectAllAppt(Instant loginTime) throws SQLException {
        String query = "SELECT * " +
                "FROM appointments " +
                "WHERE (Start >= ?) " +
                "ORDER BY Start;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setTimestamp(1, Timestamp.from(loginTime));
        return ps.executeQuery();
    }

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
        Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
        return (new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
    }

    public static void selectMonthAppt() throws SQLException {

        String query = "SELECT * FROM appointments WHERE (MONTH(?) = MONTH(Start)) AND (YEAR(?) = YEAR(Start));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
       // Timestamp ts = Timestamp.valueOf(LocalDateTime.now().format(dtf));
        String queryPara = (LocalDateTime.now().format(TimeChecks.dtf));
        //System.out.println(queryPara);
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
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
            Working.addApptMonth(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }

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
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
            Working.addApptWeek(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, customer, user));
        }
    }


    // this one is for selecting the appointments associated to a customer
    // this first one is for when making new appoointment
    public static ResultSet selectCustAppts(int custId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE (Customer_ID = ?);";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    // this one is for updating so it excludes the one being updated.
    public static ResultSet selectCustAppts(int custId, int apptId) throws SQLException {
        String query = "SELECT * FROM appointments WHERE ((Customer_ID = ?) AND (Appointment_ID != ?));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.setInt(2, apptId);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    /*
    === Add, Delete, Modify Queries
     */
    public static void insertAppointment(String title, String desc, String loc, String type, Instant startDT,
                                         Instant endDT, int custId, int userId, int contId) throws SQLException {
        String query = "INSERT INTO appointments " +
        " (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
        "Customer_ID, User_ID, Contact_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?);"; //everything but the ID
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, title); //title
        ps.setString(2, desc); //desc
        ps.setString(3, loc); //locatio
        ps.setString(4, type); //type
        ps.setTimestamp(5, Timestamp.from(startDT)); //start date time
        ps.setTimestamp(6, Timestamp.from(endDT)); //end date time
        ps.setString(7, Working.getLoggedInName() ); //created by
        ps.setString(8, Working.getLoggedInName());//last updated by
        ps.setInt(9, custId); // customer id
        ps.setInt(10, userId); //user id
        ps.setInt(11, contId); // contact id
        ps.executeUpdate();
    }

    public static void updateAppointment(int apptId, String title, String desc, String loc, String type, Instant startDt,
                                         Instant endDt, int custId, int userId, int contId) throws SQLException {
        String query = "UPDATE appointments " +
                "SET Title = ?, " + //1
                "Description = ?, " + //2
                "Location = ?, " +//3
                "Type = ?, " +//4
                "Start = ?, " +//5
                "End = ?, " +//6
                "Last_Update = NOW()," +
                "Last_Updated_By = ?," +//7
                "Customer_ID = ?," +//8
                "User_ID = ?," + //9
                "Contact_ID = ? " + //10
                "WHERE Appointment_ID = ?;"; //11
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

    public static void deleteAppointment(int apptId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, apptId);
        ps.executeUpdate();
    }

    public static void delCustAppts(int custId) throws SQLException {
        String query = "DELETE FROM appointments WHERE Customer_ID = ?;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, custId);
        ps.executeUpdate();
    }

}
