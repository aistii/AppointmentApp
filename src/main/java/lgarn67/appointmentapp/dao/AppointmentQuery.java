package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentQuery {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contId = rs.getInt("Contact_ID");
            assert contId != 0 : "Contact Not Foun";
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
            Working.addAppointment(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, custId, userId));
        }
    }

    public static void selectMonthAppt() throws SQLException {

        String query = "SELECT * FROM appointments WHERE (MONTH(?) = MONTH(Start)) AND (YEAR(?) = YEAR(Start));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
       // Timestamp ts = Timestamp.valueOf(LocalDateTime.now().format(dtf));
        String queryPara = (LocalDateTime.now().format(dtf));
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
            LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contId = rs.getInt("Contact_ID");
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
            Working.addApptMonth(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, custId, userId));
        }
    }

    public static void selectWeekAppt() throws SQLException {

        String query = "SELECT * FROM appointments WHERE (WEEK(?) = WEEK(start)) AND (WEEK(?) = WEEK(start));";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, (LocalDateTime.now()).format(dtf));
        ps.setString(2, (LocalDateTime.now()).format(dtf));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startLDT = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endLDT = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contId = rs.getInt("Contact_ID");
            Contact cont = ContactQuery.contactFetch(contId); // need to change to contact object
            Working.addApptWeek(new Appointment(id, title, desc, loc, cont, type, startLDT, endLDT, custId, userId));
        }
    }

    /*
    === Add, Delete, Modify Queries
     */
    public static void insertAppointment(String title, String desc, String loc, String type, LocalDateTime startDT,
                                         LocalDateTime endDT, int custId, int userId, int contId) throws SQLException {
        String query = "INSERT INTO appointments " +
        " (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
        "Customer_ID, User_ID, Contact_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?);"; //everything but the ID
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setString(1, title); //title
        ps.setString(2, desc); //desc
        ps.setString(3, loc); //locatio
        ps.setString(4, type); //type
        ps.setTimestamp(5, Timestamp.valueOf(startDT)); //start date time
        ps.setTimestamp(6, Timestamp.valueOf(endDT)); //end date time
        ps.setString(7, Working.getLoggedInName() ); //created by
        ps.setString(8, Working.getLoggedInName());//last updated by
        ps.setInt(9, custId); // customer id
        ps.setInt(10, userId); //user id
        ps.setInt(11, contId); // contact id
        ps.executeQuery();

    }

}
