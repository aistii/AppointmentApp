package lgarn67.appointmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lgarn67.appointmentapp.controller.login_ctlr;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.dbconnection;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class App extends Application {

    ResourceBundle rbFR = ResourceBundle.getBundle("language_fr");
    ResourceBundle rbEN = ResourceBundle.getBundle("language_en");

    @Override
    public void start(Stage stage) throws IOException {
        dbconnection.openConnection();
        if ((Locale.getDefault()).toString().equals("fr")) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"), rbFR);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Appointment Scheduler");
            stage.setScene(scene);
            stage.show();
            //System.out.println("Locale is in French");
            //TODO
            // need to maybe call the login controller to edit text field
        } else {
            //System.out.println("Locale is in English");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"), rbEN);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Appointment Scheduler");
            stage.setScene(scene);
            stage.show();
        }


    }


    public static void main(String[] args) throws SQLException {
        LocalDate myLocalDate = LocalDate.now();
        LocalTime myLocalTime = LocalTime.now();
        ZoneId myTimeZone = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime myZDT = ZonedDateTime.of(myLocalDate, myLocalTime, myTimeZone);

        ZoneId parisZone = ZoneId.of("Europe/Paris");

        Instant meToUTC = myZDT.toInstant();

        ZonedDateTime parisZDT = meToUTC.atZone(parisZone);

        System.out.println("Local Date Time: " + myZDT);
        System.out.println("Paris Date Time: " + parisZDT);
        LocalTime startEasternHour = LocalTime.of(8,00);
        LocalTime endEasternHour = LocalTime.of(22, 00);
        LocalTime myTime = LocalTime.now();
        if (myTime.isBefore(endEasternHour) && myTime.isAfter(startEasternHour)) {
            System.out.println("You are in business hours! Yay");
        } else {
            System.out.println("You are missed business hours!");
        }

        System.out.println("Add 14 hours to start time: " + startEasternHour.plusHours(14));
        LocalDate parisDate = LocalDate.now();
        LocalTime parisTime = LocalTime.now();

        //Locale.setDefault(new Locale("fr"));

    /*int startTimeH = myZDT.getHour();
    int startTimeM = myZDT.getMinute();
    LocalTime myNewStartTime = LocalTime.of(startTimeH, startTimeM);
    return (myNewStartTime.isAfter(LocalTime.of(8,0)));*/

        launch();
        dbconnection.closeConnection();
    }
}