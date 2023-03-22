package lgarn67.appointmentapp.dao;

import lgarn67.appointmentapp.model.FLDReport;
import lgarn67.appointmentapp.model.MonthTypeReport;
import lgarn67.appointmentapp.model.Working;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsQuery {
    public static ResultSet qContactSchedule(int contId) throws SQLException {
        String query = " SELECT * FROM appointments WHERE (Contact_ID = ?) ORDER BY Start;" ;
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ps.setInt(1, contId);
        return ps.executeQuery();
    }
    public static void qMTCountRep() throws SQLException {
        String query = "SELECT MONTHNAME(appt.Start) as Appt_Month, appt.Type, COUNT(customers.Customer_ID) as appt_ct " +
                "FROM appointments as appt " +
                "INNER JOIN customers " +
                "ON appt.Customer_ID = customers.Customer_ID " +
                "GROUP BY Appt_Month, Type " +
                "ORDER BY Appt_Month;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String month = rs.getString("Appt_Month");
            String type = rs.getString("Type");
            int count = rs.getInt("appt_ct");
            Working.addToMTRep(new MonthTypeReport(month, type, count));
        }
    }
    public static void qFLDCountRep() throws SQLException {
        String query = "SELECT countries.Country, fld.Division, COUNT(customers.Customer_ID) as customer_count " +
                "FROM countries " +
                "INNER JOIN first_level_divisions AS fld " +
                "ON countries.Country_ID = fld.Country_ID " +
                "INNER JOIN customers " +
                "ON fld.Division_ID = customers.Division_ID " +
                "GROUP BY countries.Country, fld.Division " +
                "ORDER BY customer_count DESC;";
        PreparedStatement ps = dbconnection.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String country = rs.getString("Country");
            String fld = rs.getString("Division");
            int count = rs.getInt("customer_count");
            Working.addToFLDRep(new FLDReport(country, fld, count));
        }
    }
}
