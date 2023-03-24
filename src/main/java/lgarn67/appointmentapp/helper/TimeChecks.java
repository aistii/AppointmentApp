package lgarn67.appointmentapp.helper;

import lgarn67.appointmentapp.controller.alert_ctlr;
import lgarn67.appointmentapp.dao.AppointmentQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Class that performs various checks on time, for appointment time validity, overlaps, and upcoming appointments
 */
public class TimeChecks {
    /**
     * Retrieves the UTC Zone ID
     *
     * @return the the UTC Zone ID
     */
    public static ZoneId getUtcZoneId() {
        return utcZoneId;
    }

    /**
     * Retrieves the user's local time zone ID
     *
     * @return the user's local time zone ID
     */
    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    /**
     * Retrieves the US-Eastern time zone ID ("America/New_York").
     *
     * @return the US eastern zone ID
     */
    public static ZoneId getEastZoneId() {
        return eastZoneId;
    }

    /**
     * UTC zone id.
     */
    private static final ZoneId utcZoneId = ZoneId.of("UTC");
    /**
     * User's local zone id.
     */
    private static final ZoneId localZoneId = ZoneId.systemDefault();
    /**
     * US Eastern zone id.
     */
    private static final ZoneId eastZoneId = ZoneId.of("US/Eastern");

    /**
     * Date time formatter to format the time values into what the database can use.
     */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Checks the inputted start time against the business hours to ensure it is on or after 8AM eastern.
     *
     * @param userStartTime the start time
     * @return whether or not the start time is on or after 8AM eastern.
     */

    public static boolean checkStartingTime(ZonedDateTime userStartTime) {
        LocalTime startBizTime = LocalTime.of(8,0);
        int startTimeH = userStartTime.getHour();
        int startTimeM = userStartTime.getMinute();
        LocalTime timeToTest = LocalTime.of(startTimeH, startTimeM);
        return timeToTest.isAfter(startBizTime) || timeToTest.equals(startBizTime);
    }

    /**
     * Checks the inputted end time against the business hours to ensure it is on or before 10PM eastern.
     * Also checks that the end time is not before the start date-time.
     *
     * @param userStartTime the user start date-time
     * @param userEndTime   the user end date-time
     * @return whether or not the end time is valid
     */
    public static boolean checkEndingTime(ZonedDateTime userStartTime, ZonedDateTime userEndTime) {


        LocalTime startBizTime = LocalTime.of(8,0);
        LocalTime endBizTime = LocalTime.of(22,0);
        int endDTYear = userStartTime.getYear();
        int endDTMonth = userStartTime.getMonthValue();
        int endDTDay = userStartTime.getDayOfMonth();
        LocalDate endDateThreshold = LocalDate.of(endDTYear,endDTMonth,endDTDay);
        ZonedDateTime startBizDateTime = ZonedDateTime.of(endDateThreshold, startBizTime, getEastZoneId());
        ZonedDateTime endBizDateTime = ZonedDateTime.of(endDateThreshold, endBizTime, getEastZoneId());
        if (userStartTime.isBefore(userEndTime)) {
            return (userEndTime.isAfter(startBizDateTime)) &&
                    (userEndTime.isBefore(endBizDateTime) || userEndTime.equals(endBizDateTime));
        } else {
            return false;
        }
    }

    /**
     * Check on all three cases of overlaps; if any return true, the appointment add controller will not allow the appointment to be made with the inputted times.
     *
     * @param start  the start date-time
     * @param end    the end date-time
     * @param custId the customer's id
     * @return whether or not overlaps exist
     */
    public static boolean checkOverlap(ZonedDateTime start, ZonedDateTime end, int custId) throws SQLException {
        return (checkMiddleVio(start, end, custId) ||
                checkStartVio(start, end, custId) ||
                checkEndVio(start, end, custId));
    }

    /**
     * Check on all three cases of overlaps; if any return true, the appointment add/edit controllers will not allow the appointment to be made with the inputted times.
     * This one is specifically used for when an appointment is being edited; the appointment ID parameter will exclude the appointment from the result sets.
     *
     * @param start  the start date-time
     * @param end    the end date-time
     * @param custId the customer's ID
     * @param apptId the appointment ID
     * @return whether or not overlaps exist
     */
    public static boolean checkOverlap(ZonedDateTime start, ZonedDateTime end, int custId, int apptId) throws SQLException {
        return (checkMiddleVio(start, end, custId, apptId) ||
                checkStartVio(start, end, custId, apptId) ||
                checkEndVio(start, end, custId, apptId));
    }

    /**
     * Checks for overlap where the beginning of a preexisting appointment can cause an overlap with the new appointment.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @return whether or not overlap exists
     */

    public static boolean checkStartVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));

        while (rs.next()) {
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            if (((apptStart.isAfter(wishStart))||(apptStart.equals(wishStart))) && (apptStart.isBefore(wishEnd))) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Checks for overlap where the beginning of a preexisting appointment can cause an overlap with the appointment's edited times.
     *  Excludes the appointment being edited.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @param apptId    the appointment ID
     * @return whether or not overlap exists
     */
    public static boolean checkStartVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId, int apptId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId, apptId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()) {
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            if (((apptStart.isAfter(wishStart))||(apptStart.equals(wishStart))) && (apptStart.isBefore(wishEnd))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for overlap where the end of a preexisting appointment can cause an overlap with the new appointment.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @return whether or not overlap exists
     */
    public static boolean checkEndVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()) {
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if ((apptEnd.isAfter(wishStart)) && ((apptEnd.equals(wishEnd))||(apptEnd.isBefore(wishEnd)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for overlap where the end of a preexisting appointment can cause an overlap with the appointment's new times.
     * Excludes the appointment being edited.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @param apptId    the appointment ID
     * @return whether or not overlap exists
     */
    public static boolean checkEndVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId, int apptId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId, apptId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()) {
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if ((apptEnd.isAfter(wishStart)) && ((apptEnd.equals(wishEnd))||(apptEnd.isBefore(wishEnd)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for overlap where the beginning and end of a preexisting appointment totally overlaps with the new appointment.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @return whether or not overlap exists
     */
    public static boolean checkMiddleVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()){
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if (((apptStart.isBefore(wishStart))||(apptStart.equals(wishStart))) && ((apptEnd.isAfter(wishEnd))||(apptEnd.isEqual(wishEnd)))){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for overlap where the beginning and end of a preexisting appointment totally overlaps with the appointment's new times.
     * Excludes the appointment being edited.
     *
     * @param userStart the user start date-time
     * @param userEnd   the user end date-time
     * @param custId    the customer ID
     * @param apptId    the appointment ID
     * @return whether or not overlap exists
     */
    public static boolean checkMiddleVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId, int apptId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId, apptId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()){
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if (((apptStart.isBefore(wishStart))||(apptStart.equals(wishStart))) && ((apptEnd.isAfter(wishEnd))||(apptEnd.isEqual(wishEnd)))){
                return true;
            }
        }
        return false;
    }
    /**
     * <p>Checks if any appointment that comes after the moment of user log in is within 15 minutes.
     * It will alert the user if there is; a popup will still appear if there isn't, but should there not be any upcoming appointments, it will say there are no appointments within the next 15 minutes.</p>
     * <p><b>Note on the Result Set:</b> I ran into an issue of both alerts showing up. It took a while to figure out how to make this exclusive.
     * I tested out the "isClosed()" to return a boolean to see if the actual result set was closed or not.</p>
     * <p>From this Stack Overflow: https://stackoverflow.com/questions/4507440/must-jdbc-resultsets-and-statements-be-closed-separately-although-the-connection</p>
     * <p>I learned that the result set doesn't get closed even if it iterates through the entire thing. I used the status of the result set then as a way to determine if there were associated appointments.</p>
     * <p>The "rs.close()" before the break in the while loop explicitly closes the result set; that is the signal that a soon appointment was found.</p>
     *
     * @param provLoginTime the provided login time.
     */
    public static void checkSoonAppt(LocalDateTime provLoginTime) throws SQLException {
        ZonedDateTime zdtLocalLoginTime = provLoginTime.atZone(getLocalZoneId());
        Instant utcLoginTime = zdtLocalLoginTime.toInstant();
        ResultSet rs = AppointmentQuery.selectAllAppt(utcLoginTime);
        while (rs.next()) {
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            long diff = ChronoUnit.MINUTES.between(zdtLocalLoginTime, apptStart);
            if (evalSoon(diff)) {
                int apptId = (rs.getInt("Appointment_ID"));
                ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
                alert_ctlr.loadLoginAlert(true, apptId, apptStart, apptEnd);
                rs.close();
                break;
            }
        }
        if (!rs.isClosed()){
            alert_ctlr.loadLoginAlert(false, 0, null, null);
        }


    }
    /**
     * Checks if the time difference between the time of login and the start time of a appointment has a difference of 15 minutes or less.
     *
     * @param timeDiff the time difference calculated from checkSoonAppointment
     * @return whether or not the difference in time between the login time and appointment start time is within 15 minutes.
     */
    public static boolean evalSoon (long timeDiff) {
        return (0 < timeDiff && timeDiff <= 15);
    }

}

