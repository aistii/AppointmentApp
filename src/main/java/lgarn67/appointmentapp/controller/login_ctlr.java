package lgarn67.appointmentapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.loginConnect;

public class login_ctlr implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //uNameError.setText(null);
        //pWordError.setText(null);
        uNameField.setText(null);
        pwdField.setText(null);
    }
    Stage stage;
    Parent scene;
    @FXML private ComboBox<?> languageField;
    @FXML private static Label languageHeader;
    @FXML private static Button loginBtn;
    @FXML private static Label loginTitle;
    @FXML private Label pWordError;
    @FXML private static Label passWordTitle;
    @FXML private  TextField pwdField;
    @FXML private static Label timeZoneHeader;
    @FXML private static Label timeZoneLabel;
    @FXML private Label uNameError;
    @FXML private TextField uNameField;
    @FXML private static Label userNameTitle;

    // static ResourceBundle rbFR = ResourceBundle.getBundle("language_fr");
    // static ResourceBundle rbEN = ResourceBundle.getBundle("language_en");

    // clickLogin() will verify the log in and change the scenes, and also populate the first table we go to
    // which is in the customer view.
    @FXML void clickLogin(ActionEvent event) throws IOException, SQLException {
        // The order of things that happen:
        String attemptUName = uNameField.getText();
        String attemptPWord = pwdField.getText();

        if ((uNameField.getText() == null) || pwdField.getText() == null) {
            uNameError.setVisible(true);
            pWordError.setVisible(true);
        } else {
            if (loginConnect.checkLogin(attemptUName, attemptPWord)) {
                int fetchedUserId = loginConnect.fetchId(attemptUName, attemptPWord);
                Working.setLoggedInId(fetchedUserId);// will pass to screen
                //TODO
                // Need to eventually implement the thing that holds user id in the duration of the app running
                // in order to successfully show who modified the appointment or user last.
                CustomerQuery.selectAll();
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                uNameError.setVisible(true);
                pWordError.setVisible(true);
            }
        }
        //TODO
        // Need to pass in the values typed into the fields to the loginConnect helper java file.

    }

}