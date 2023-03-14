package lgarn67.appointmentapp.controller;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lgarn67.appointmentapp.Country;
import lgarn67.appointmentapp.Division;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.dao.CountryQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.DivisionQuery;

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
            CountryQuery.getCountries();
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

    Border errorStyle = new Border(new BorderStroke(Color.valueOf("#EF596F"), BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
    Border clearStyle = new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.NONE, null, new BorderWidths(0)));

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
        DivisionQuery.getRelatedDivisions(id);
    }

    @FXML void ClickCancel(ActionEvent e) throws IOException {
        resetFields();
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // First it will check if checkCompletion() returns true. It will just be an "if" statement at the highest level.
    // The number formatting should not be too much of an issue since most of these things are a string/can't be taken the wrong way
    @FXML void ClickSaveCust(ActionEvent event) throws SQLException, IOException {
        // Checks that fields are filled first;
        // and then after that, the lengths of text field.
        // It is nested though.. you will have to finish all fields first.
        if (checkCompletion()){
            if (checkLength()) {
                String custName = NameField.getText();
                String custAddr = AddrField.getText();
                String custPost = PostField.getText();
                String custPhone = PhoneField.getText();
                int custFld = FLDCombo.getSelectionModel().getSelectedItem().getDivisionId();
                CustomerQuery.insertCustomer(custName, custAddr, custPost, custPhone, custFld);
                resetFields();
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    // This checks for completion so that no NullErrorPointer exception is thrown.
    // That way, there is just parsing that needs to be done, and I can catch type errors.
    public boolean checkCompletion () {

        nameErr.setText(null);
        AddrErr.setText(null);
        PostErr.setText(null);
        PhoneErr.setText(null);
        CtryErr.setText(null);
        FLDErr.setText(null);
        NameField.setBorder(clearStyle);
        AddrField.setBorder(clearStyle);
        PostField.setBorder(clearStyle);
        PhoneField.setBorder(clearStyle);
        CountryCombo.setBorder(clearStyle);
        FLDCombo.setBorder(clearStyle);

        // Checks if ANY field is empty (which will return false), then will individually mark which ones are a problem.
        if (NameField.getText().isBlank() || AddrField.getText().isBlank() || PostField.getText().isBlank() ||
            PhoneField.getText().isBlank() || CountryCombo.getSelectionModel().isEmpty() || FLDCombo.getSelectionModel().isEmpty()){
            if (NameField.getText().isBlank()){
                NameField.setBorder(errorStyle);
                nameErr.setText("Please enter a name.");
            }
            if (AddrField.getText().isBlank()){
                AddrField.setBorder(errorStyle);
                AddrErr.setText("Please enter an address.");
            }
            if (PostField.getText().isBlank()){
                PostField.setBorder(errorStyle);
                PostErr.setText("Please enter a postal code.");
            }
            if (PhoneField.getText().isBlank()){
                PhoneField.setBorder(errorStyle);
                PhoneErr.setText("Please enter a phone number.");
            }
            if (CountryCombo.getSelectionModel().isEmpty()){
                CountryCombo.setBorder(errorStyle);
                CtryErr.setText("Please select a country.");
            }
            if (FLDCombo.getSelectionModel().isEmpty()){
                FLDCombo.setBorder(errorStyle);
                FLDErr.setText("Please select a division.");
            }
            return false;
        } else {
            // if no fields are empty it will return true.
            return true;
        }
    }
    //TODO
    // Making a method to check the field lengths for the fields that will insert a
    // VARCHAR value into the database.
    // For Reference:
    // 1) Customer Name maximum 50 chars
    // 2) Address maximum 100
    // 3) Phone maximum 50 chars
    // 4) Postal Code max 50 char

    public boolean checkLength() {
        // Checks if length of any field is over maximum,
        // then checks for specific field, similar to the check completion method.
        // If method produces "false", the entry will not be saved.
        int nameLength = NameField.getLength(); // (nameLength > 50)
        int addrLength = AddrField.getLength(); // (addrLength > 100)
        int phoneLength = PhoneField.getLength(); // (phoneLength > 50)
        int postLength = PostField.getLength(); // (postLength > 50)
        // this first checks if any are over the limit. even if only one is over the limit,
        // it will not allow a save until the length is fixed.
        if ((nameLength > 50)||(addrLength > 100)||(phoneLength > 50)||(postLength > 50)) {
            // each one will do the error field highlight + write a message saying
            // it's over the budget + tells how many chars there are.
            if (nameLength > 50){
                NameField.setBorder(errorStyle);
                Integer overCt = nameLength;
                nameErr.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            if (addrLength > 100){
                AddrField.setBorder(errorStyle);
                Integer overCt = addrLength;
                AddrErr.setText("Maximum 100 characters; currently has " + overCt.toString() + " characters.");
            }
            if (phoneLength > 50){
                PhoneField.setBorder(errorStyle);
                Integer overCt = phoneLength;
                PhoneErr.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            if (postLength > 50){
                PostField.setBorder(errorStyle);
                Integer overCt = postLength;
                PostErr.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            return false;
        } else {
            return true;
        }
    }

    // Resets the error markings/messages and the fields themselves, a clean slate!
    public void resetFields () {
        NameField.clear();
        AddrField.clear();
        PostField.clear();
        PhoneField.clear();
        CountryCombo.getSelectionModel().select(null);
        FLDCombo.getSelectionModel().select(null);
        Working.resetCountries();
        Working.resetDivisions();

        nameErr.setText(null);
        AddrErr.setText(null);
        PostErr.setText(null);
        PhoneErr.setText(null);
        CtryErr.setText(null);
        FLDErr.setText(null);
        NameField.setBorder(clearStyle);
        AddrField.setBorder(clearStyle);
        PostField.setBorder(clearStyle);
        PhoneField.setBorder(clearStyle);
        CountryCombo.setBorder(clearStyle);
        FLDCombo.setBorder(clearStyle);

    }
}
