package lgarn67.appointmentapp.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import lgarn67.appointmentapp.model.Working;
import lgarn67.appointmentapp.helper.TimeChecks;

import java.time.ZonedDateTime;


/**
 * Controller for styling alerts in the app, as well as providing the login appointment alert and connecting to database error alert.
 */
public class alert_ctlr {

    /**
     * Takes in the alert to be made and applies the CSS to it.
     *
     * @param alert the alert to be styled
     */
    public static void loadDialogStyle(Alert alert) {
        DialogPane dia = alert.getDialogPane();
        dia.getStylesheets().add(alert_ctlr.class.getResource("/lgarn67/appointmentapp/styles.css").toString());
    }

    /**
     * Load login alert.
     *
     * @param success   whether or not there is an appointment coming up soon
     * @param apptId    the appointment id of upcoming appointment
     * @param startTime the start time of upcoming appointment
     * @param endTime   the end time of upcoming appointment
     */
    public static void loadLoginAlert (boolean success, int apptId, ZonedDateTime startTime, ZonedDateTime endTime) {
        if (success) {
            ZonedDateTime localizedStart = startTime.withZoneSameInstant(TimeChecks.getLocalZoneId());
            ZonedDateTime localizedEnd = endTime.withZoneSameInstant(TimeChecks.getLocalZoneId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            loadDialogStyle(alert);
            alert.setTitle("Welcome!");
            alert.setHeaderText("Welcome! There is an appointment starting within 15 minutes.");
            alert.setContentText("Hello, " + Working.getLoggedInName() + ". " +
                    "Appointment #" + apptId + " starts soon!\n"+
                    "Start Time: " + localizedStart.toLocalDateTime().format(TimeChecks.dtf) +"\n" +
                    "End Time: " + localizedEnd.toLocalDateTime().format(TimeChecks.dtf));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            loadDialogStyle(alert);
            alert.setTitle("Welcome!");
            alert.setHeaderText("Welcome!");
            alert.setContentText("Hello, " +Working.getLoggedInName()+". "+
                    "There are no appointments starting within 15 minutes.");
            alert.showAndWait();
        }
    }

    /**
     * Should there be an error connecting to the database, it will say that it couldn't connect to the database.
     */
    public static void errorConnecting() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        loadDialogStyle(alert);
        alert.setTitle("Error Connecting to Database");
        alert.setHeaderText("Error Connecting to Database");
        alert.setContentText("Could not connect to database.");
        alert.showAndWait();
    }
}
