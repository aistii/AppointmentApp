package lgarn67.appointmentapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lgarn67.appointmentapp.dao.loginConnect;
import lgarn67.appointmentapp.helper.Logger;
import lgarn67.appointmentapp.helper.TimeChecks;
import lgarn67.appointmentapp.model.Working;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * The controller for the login scene.
 */
public class login_ctlr implements Initializable {

    /**
     * Initializes the scene with the user's computer's local time zone label and sets the username and passwords field to null.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId detectedZone = ZoneId.systemDefault();
        timeZoneLabel.setText(detectedZone.toString());
        uNameField.setText(null);
        pwdField.setText(null);
    }

    /**
     * The Stage/stage.
     */
    Stage stage;
    /**
     * The Scene/view.
     */
    Parent scene;
    /**
     * The error message for password field.
     */
    @FXML private Label pWordError;
    /**
     * The password field.
     */
    @FXML private  TextField pwdField;
    /**
     * The label to display user's current time zone.
     */
    @FXML private Label timeZoneLabel;
    /**
     * The error message for username field.
     */
    @FXML private Label uNameError;
    /**
     * The username field.
     */
    @FXML private TextField uNameField;

    /**
     * Checks to see if the login information the user entered matches a row against the database.
     * It will log any login attempts in a login_activity file.
     * It will check for appointments to begin within the next 15 minutes and alert the user if there is.
     * If there are no appointments within the 15 minutes of login, the alert will reflect that.
     */
    @FXML void clickLogin(ActionEvent event) throws IOException, SQLException {
        String attemptUName = uNameField.getText();
        String attemptPWord = pwdField.getText();

        if ((uNameField.getText() == null) || pwdField.getText() == null) {
            Logger.appendLog(null, LocalDateTime.now(), false);
            uNameError.setVisible(true);
            pWordError.setVisible(true);
        } else {
            if (loginConnect.checkLogin(attemptUName, attemptPWord)) {
                int fetchedUserId = loginConnect.fetchId(attemptUName, attemptPWord);
                String fetchedUsername = loginConnect.fetchUsername(attemptUName, attemptPWord);
                Working.setLoggedInId(fetchedUserId);
                Working.setLoggedInName(fetchedUsername);
                Logger.appendLog(fetchedUsername, LocalDateTime.now(), true);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                LocalDateTime loginTime = LocalDateTime.now();
                TimeChecks.checkSoonAppt(loginTime);
            } else {
                Logger.appendLog(attemptUName, LocalDateTime.now(), false);
                uNameError.setVisible(true);
                pWordError.setVisible(true);
            }
        }
    }




}