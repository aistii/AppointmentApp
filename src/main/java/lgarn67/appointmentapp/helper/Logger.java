package lgarn67.appointmentapp.helper;

import lgarn67.appointmentapp.dao.AppointmentQuery;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Logger {
    public static void appendLog(String userName, LocalDateTime loginDT, boolean success) throws IOException {
        String fileName = "login_activity.txt";
        FileWriter fw = new FileWriter(fileName, true);
        PrintWriter out = new PrintWriter(fw);
        ZonedDateTime localLogin = ZonedDateTime.of(loginDT, TimeChecks.getLocalZoneId());
        ZonedDateTime loginUTC = localLogin.withZoneSameInstant(TimeChecks.getUtcZoneId());
        System.out.println(loginUTC);
        String successStatus = isSuccessful(success);
        out.println("Attempted UserName: "+userName+"\t" +
                "Login Time: " + loginUTC.format(TimeChecks.dtf)+ "@UTC\t\t" +
                successStatus);
        out.close();
    }

    public static String isSuccessful(boolean success) {
        if (success) {
            return "Successful Login";
        } else {
            return "Failed Login";
        }
    }

}
