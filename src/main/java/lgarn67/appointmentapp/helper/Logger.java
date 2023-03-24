package lgarn67.appointmentapp.helper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Class that logs login attempts to the login_activity.txt file in the app's root.
 */
public class Logger {
    /**
     * Appends a line to the text file with the attempted username, timestamp, and whether or not the attempt was successful.
     *
     * @param userName the attempted username
     * @param loginDT  the login date-time
     * @param success  whether or not login was successful
     */
    public static void appendLog(String userName, LocalDateTime loginDT, boolean success) throws IOException {
        String fileName = "login_activity.txt";
        FileWriter fw = new FileWriter(fileName, true);
        PrintWriter out = new PrintWriter(fw);
        ZonedDateTime localLogin = ZonedDateTime.of(loginDT, TimeChecks.getLocalZoneId());
        ZonedDateTime loginUTC = localLogin.withZoneSameInstant(TimeChecks.getUtcZoneId());
        String successStatus = isSuccessful(success);
        out.println("Attempted UserName: "+userName+"\t\t" +
                "Login Time: " + loginUTC.format(TimeChecks.dtf)+ "@UTC\t\t" +
                successStatus);
        out.close();
    }

    /**
     * Takes the value of the boolean in appendLog and provides a string message indicating if the login was successful or not.
     * This would make more sense in the context of the log over "true" or "false" at the end to indicate login success.
     *
     * @param success whether or not the login was successful
     * @return the string message of successful or failed login.
     */
    public static String isSuccessful(boolean success) {
        if (success) {
            return "Successful Login";
        } else {
            return "Failed Login";
        }
    }

}
