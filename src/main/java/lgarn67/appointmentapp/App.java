package lgarn67.appointmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lgarn67.appointmentapp.dao.dbconnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class containing the main method to run the application.
 */
public class App extends Application {

    /**
     * The resource bundle for French; used to translate the login screen.
     */
    ResourceBundle rbFR = ResourceBundle.getBundle("language_fr");
    /**
     * The resource bundle for English; used to translate the login screen.
     */
    ResourceBundle rbEN = ResourceBundle.getBundle("language_en");

    /**
     * Attempts to open a connection to the database.
     * If there is failure to connect, error alert will show up and the program is terminated.
     * If successful it will load the login screen.
     * If the locale is in French, the login screen will be written in French. If not, it will be in English.
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        dbconnection.openConnection();
        try {
            if (dbconnection.connection.isValid(0)) {
                if ((Locale.getDefault()).toString().equals("fr")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"), rbFR);
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Appointment Scheduler");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"), rbEN);
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Appointment Scheduler");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    /**
     * The entry point of application.
     */
    public static void main(String[] args) throws SQLException {
        launch();
        dbconnection.closeConnection();
    }
}