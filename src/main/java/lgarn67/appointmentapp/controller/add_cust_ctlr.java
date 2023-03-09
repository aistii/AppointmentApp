package lgarn67.appointmentapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lgarn67.appointmentapp.Country;
import lgarn67.appointmentapp.Customer;
import lgarn67.appointmentapp.Division;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.dao.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class add_cust_ctlr implements Initializable {

Stage stage;
Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomerQuery.getCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CountryCombo.getItems().clear();
        CountryCombo.setItems(Working.getAllCountries());
        CountryCombo.setPromptText("Select a country...");
        FLDCombo.setPromptText("Please select a country first.");
    }

    @FXML private Button SaveBtn;
    @FXML private Button CancelBtn;
    @FXML private TextField IdField;
    @FXML private TextField NameField;
    @FXML private TextField AddrField;
    @FXML private TextField PostField;
    @FXML private TextField PhoneField;
    @FXML private ComboBox<Country> CountryCombo;
    @FXML private ComboBox<Division> FLDCombo;
    @FXML private Label nameErr;
    @FXML private Label PostErr;
    @FXML private Label CtryErr;
    @FXML private Label AddrErr;
    @FXML private Label FLDErr;
    @FXML private Label PhoneErr;

    @FXML void onCountrySelected() throws SQLException {
        FLDCombo.getItems().clear();
        try {
            int ctryId = CountryCombo.getSelectionModel().getSelectedItem().getId();
            passCountryId(ctryId);
        } catch (NullPointerException e) {
            // Nothing will happen don't worry about it dawg
        }

        FLDCombo.setPromptText("Select first-level division...");
        FLDCombo.setItems(Working.getAllDivisions());
    }

    public void passCountryId(int id) throws SQLException{
        CustomerQuery.getRelatedDivisions(id);
    }

    @FXML void ClickCancel(ActionEvent e) throws IOException {
        Working.resetCountries();
        Working.resetDivisions();
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void ClickSaveCust(ActionEvent event) {

    }
}
