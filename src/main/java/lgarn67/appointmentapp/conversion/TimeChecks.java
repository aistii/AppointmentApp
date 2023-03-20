package lgarn67.appointmentapp.conversion;

import lgarn67.appointmentapp.Appointment;
import lgarn67.appointmentapp.controller.alert_ctlr;
import lgarn67.appointmentapp.dao.AppointmentQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class TimeChecks {
    public static ZoneId getUtcZoneId() {
        return utcZoneId;
    }

    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    public static ZoneId getEastZoneId() {
        return eastZoneId;
    }

    // Time Zones in Play
    private static ZoneId utcZoneId = ZoneId.of("UTC");
    private static ZoneId localZoneId = ZoneId.systemDefault();
    private static ZoneId eastZoneId = ZoneId.of("US/Eastern");

    // Takes in the ZDT from Start Date and Time section
    public static boolean checkStartingTime(ZonedDateTime userStartTime) {
        // The logic here
        LocalTime startBizTime = LocalTime.of(8,0);
        int startTimeH = userStartTime.getHour();
        int startTimeM = userStartTime.getMinute();
        LocalTime timeToTest = LocalTime.of(startTimeH, startTimeM);
        if (timeToTest.isAfter(startBizTime) || timeToTest.equals(startBizTime)){
            return true;
        } else {
            return false;
        }
    }
    public static boolean checkEndingTime(ZonedDateTime userStartTime, ZonedDateTime userEndTime) {
        // With the order it checks, it will check if the start time is correct first, so this is why
        // we can check with the already validated user start time.
        // Takes in the day of the calendar from input

        LocalTime startBizTime = LocalTime.of(8,0);
        LocalTime endBizTime = LocalTime.of(22,0);
        int endDTYear = userStartTime.getYear();
        int endDTMonth = userStartTime.getMonthValue();
        int endDTDay = userStartTime.getDayOfMonth();
        LocalDate endDateThreshold = LocalDate.of(endDTYear,endDTMonth,endDTDay);
        ZonedDateTime startBizDateTime = ZonedDateTime.of(endDateThreshold, startBizTime, getEastZoneId());
        ZonedDateTime endBizDateTime = ZonedDateTime.of(endDateThreshold, endBizTime, getEastZoneId());
        if (userStartTime.isBefore(userEndTime)) {
            if ((userEndTime.isAfter(startBizDateTime)) &&
                (userEndTime.isBefore(endBizDateTime) || userEndTime.equals(endBizDateTime))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Section for Appointment Checks

    // Check on all three cases
    public static boolean checkOverlap(ZonedDateTime start, ZonedDateTime end, int custId) throws SQLException {
        // this method returns "true" if any of the cases causes overlap.
        // if it doesn't then it is not overlapping.
        return (checkMiddleVio(start, end, custId) ||
                checkStartVio(start, end, custId) ||
                checkEndVio(start, end, custId));
    }

    // Check on all three for edit scenario
    public static boolean checkOverlap(ZonedDateTime start, ZonedDateTime end, int custId, int apptId) throws SQLException {
        // this method returns "true" if any of the cases causes overlap.
        // if it doesn't then it is not overlapping.
        return (checkMiddleVio(start, end, custId, apptId) ||
                checkStartVio(start, end, custId, apptId) ||
                checkEndVio(start, end, custId, apptId));
    }

    // Check on Start Case
    public static boolean checkStartVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        //System.out.println("From checkStartVio || wishStart is: " + wishStart);
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        //System.out.println("From checkStartVio || wishEnd is: " + wishEnd);
        while (rs.next()) {
            //LocalDateTime apptStart = (rs.getTimestamp("Start")).toLocalDateTime();
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            //System.out.println("From checkStartVio || apptStart is: " + apptStart);
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            //System.out.println("From checkStartVio || apptEnd is: " + apptEnd);
            if (((apptStart.isAfter(wishStart))||(apptStart.equals(wishStart))) && (apptStart.isBefore(wishEnd))) {
                return true; // there is a violation
            }
        }
        return false; // no violation
    }

    // For the edit appointment case
    public static boolean checkStartVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId, int apptId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId, apptId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        //System.out.println("From checkStartVio || wishStart is: " + wishStart);
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        //System.out.println("From checkStartVio || wishEnd is: " + wishEnd);
        while (rs.next()) {
            //LocalDateTime apptStart = (rs.getTimestamp("Start")).toLocalDateTime();
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            //System.out.println("From checkStartVio || apptStart is: " + apptStart);
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            //System.out.println("From checkStartVio || apptEnd is: " + apptEnd);
            if (((apptStart.isAfter(wishStart))||(apptStart.equals(wishStart))) && (apptStart.isBefore(wishEnd))) {
                return true; // there is a violation
            }
        }
        return false; // no violation
    }

    // Check on End Case
    public static boolean checkEndVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()) {
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if ((apptEnd.isAfter(wishStart)) && ((apptEnd.equals(wishEnd))||(apptEnd.isBefore(wishEnd)))) {
                return true; // there is a violation
            }
        }
        return false; // no violation
    }

    // end case for edit scenario
    public static boolean checkEndVio(ZonedDateTime userStart, ZonedDateTime userEnd, int custId, int apptId) throws SQLException {
        ResultSet rs = AppointmentQuery.selectCustAppts(custId, apptId);
        ZonedDateTime wishStart = (userStart.withZoneSameInstant(getUtcZoneId()));
        ZonedDateTime wishEnd = (userEnd.withZoneSameInstant(getUtcZoneId()));
        while (rs.next()) {
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
            if ((apptEnd.isAfter(wishStart)) && ((apptEnd.equals(wishEnd))||(apptEnd.isBefore(wishEnd)))) {
                return true; // there is a violation
            }
        }
        return false; // no violation
    }

    // Check on Middle Case
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

    // and finally middle case's edit scenario
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

    public static void checkSoonAppt(LocalDateTime provLoginTime) throws SQLException {
        // Do conversion on localdatetime to UTC
        ZonedDateTime zdtLocalLoginTime = provLoginTime.atZone(getLocalZoneId());
        Instant utcLoginTime = zdtLocalLoginTime.toInstant();
        ResultSet rs = AppointmentQuery.selectAllAppt(utcLoginTime);
        while (rs.next()) {
            // pull start time from the RS
            ZonedDateTime apptStart = (rs.getTimestamp("Start")).toInstant().atZone(getUtcZoneId());
            long diff = ChronoUnit.MINUTES.between(zdtLocalLoginTime, apptStart);
            System.out.println("Time difference: " + diff + " minutes");
            if (evalSoon(diff)) {
                int apptId = (rs.getInt("Appointment_ID"));
                ZonedDateTime apptEnd = (rs.getTimestamp("End")).toInstant().atZone(getUtcZoneId());
                alert_ctlr.loadLoginAlert(true, apptId, apptStart, apptEnd);
                break;
            }
        }
        if (!rs.next()) {
            alert_ctlr.loadLoginAlert(false, 0, null, null);
        }
    }

    public static boolean evalSoon (long timeDiff) {
        return (0 < timeDiff && timeDiff <= 15);
    }
}

