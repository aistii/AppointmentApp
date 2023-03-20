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
        //Locale.setDefault(new Locale("fr"));
        launch();
        dbconnection.closeConnection();
    }
}