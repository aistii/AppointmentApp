package lgarn67.appointmentapp.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.*;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.conversion.TimeChecks;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.loginConnect;

import java.time.ZonedDateTime;

// This is so I can style the alert dialogs because the default one doesn't match
// the rest of the application. I am going to
public class alert_ctlr {

    // app-wide method to set the alert dialog's css
    public static void loadDialogStyle(Alert alert) {
        DialogPane dia = alert.getDialogPane();
        dia.getStylesheets().add(alert_ctlr.class.getResource("/lgarn67/appointmentapp/styles.css").toString());
    }

    // for the login alert for 15 minutes or less of appointment coming up.
    public static void loadLoginAlert (boolean success, int apptId, ZonedDateTime startTime, ZonedDateTime endTime) {
        if (success) {
            ZonedDateTime localizedStart = startTime.withZoneSameInstant(TimeChecks.getLocalZoneId());
            ZonedDateTime localizedEnd = endTime.withZoneSameInstant(TimeChecks.getLocalZoneId());
            //System.out.println("appointment soon! id = "+apptId+ "| start time = " + startTime + " | end time = " +endTime);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            loadDialogStyle(alert);
            alert.setTitle("Welcome!");
            alert.setHeaderText("Welcome! There is an appointment starting within 15 minutes.");
            alert.setContentText("Hello, " + Working.getLoggedInName() + ". " +
                    "Appointment #" + apptId + " starts soon!\n"+
                    "Start Time: " + localizedStart.toLocalDateTime().format(AppointmentQuery.dtf) +"\n" +
                    "End Time: " + localizedEnd.toLocalDateTime().format(AppointmentQuery.dtf));
            alert.showAndWait();
        } else {
            //System.out.println("no appointments soon <3");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            loadDialogStyle(alert);
            alert.setTitle("Welcome!");
            alert.setHeaderText("Welcome!");
            alert.setContentText("Hello, " +Working.getLoggedInName()+". "+
                    "There are no appointments starting within 15 minutes.");
            alert.showAndWait();
        }
    }


}
