package lgarn67.appointmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lgarn67.appointmentapp.dao.dbconnection;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        dbconnection.openConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Locale.setDefault(new Locale("fr"));
        // ResourceBundle rb = ResourceBundle.getBundle("lgarn67/appointmentapp/language", Locale.getDefault());
        launch();
        dbconnection.closeConnection();
    }
}