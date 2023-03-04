package lgarn67.appointmentapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class login_ctlr {
    Stage stage;
    Parent scene;

    @FXML private Button loginBtn;

    @FXML void clickLogin(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}