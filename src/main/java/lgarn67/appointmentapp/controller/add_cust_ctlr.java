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
import lgarn67.appointmentapp.model.Country;
import lgarn67.appointmentapp.model.Division;
import lgarn67.appointmentapp.model.Working;
import lgarn67.appointmentapp.dao.CountryQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.DivisionQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the add customer view.
 */
public class add_cust_ctlr implements Initializable {

    /**
     * The Stage/window.
     */
    Stage stage;
    /**
     * The Scene/view.
     */
    Parent scene;

    /**
     * Populates the country observable list and then populates the Country combo box with the observable list.
     * First-level division combo box depends on the selection of the country, so it is not populated at first.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CountryQuery.getAllCountries();
        } catch (SQLException e) {
        }
        CountryCombo.getItems().clear();
        CountryCombo.setItems(Working.getAllCountries());
        CountryCombo.setPromptText("Select a country...");
        FLDCombo.setPromptText("Please select a country first.");
    }

    /**
     * The customer name field.
     */
    @FXML private TextField NameField;
    /**
     * The customer address field.
     */
    @FXML private TextField AddrField;
    /**
     * The customer postal code field.
     */
    @FXML private TextField PostField;
    /**
     * The customer phone number field.
     */
    @FXML private TextField PhoneField;
    /**
     * The combo box for selecting customer's country.
     */
    @FXML private ComboBox<Country> CountryCombo;
    /**
     * The combo box for selecting the customer's first-level division.
     */
    @FXML private ComboBox<Division> FLDCombo;
    /**
     * The error message for the name field.
     */
    @FXML private Label nameErr;
    /**
     * The error message for the postal code field.
     */
    @FXML private Label PostErr;
    /**
     * The error message for the country combo box.
     */
    @FXML private Label CtryErr;
    /**
     * The error message for the address field.
     */
    @FXML private Label AddrErr;
    /**
     * The error message for the first-level division combo box.
     */
    @FXML private Label FLDErr;
    /**
     * The error message for the phone number field.
     */
    @FXML private Label PhoneErr;

    /**
     * The border style used to denote that a field has an error.
     */
    Border errorStyle = new Border(new BorderStroke(Color.valueOf("#EF596F"), BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
    /**
     * The "default" (errorless) style used on fields if errorStyle had been applied prior.
     */
    Border clearStyle = new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.NONE, null, new BorderWidths(0)));

    /**
     * When the user makes a choice in the country combo box, it will fetch the list of first level divisions that belong to that country.
     */
    @FXML void onCountrySelected() throws SQLException {
        FLDCombo.getItems().clear();
        try {
            int ctryId = CountryCombo.getSelectionModel().getSelectedItem().getId();
            passCountryId(ctryId);
        } catch (NullPointerException e) {
        }

        FLDCombo.setPromptText("Select first-level division...");
        FLDCombo.setItems(Working.getAllDivisions());
    }

    /**
     * Makes a call to the getAllRelatedDivisions method in the Division query class.
     * It will populate the first-level divisions combo box.
     *
     * @param id the country id
     */
    public void passCountryId(int id) throws SQLException{
        DivisionQuery.getAllRelatedDivisions(id);
    }

    /**
     * Exits the add customer function without saving.
     */
    @FXML void ClickCancel(ActionEvent e) throws IOException {
        resetFields();
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Runs validation checks on the fields. If successful, it adds the customer to the database.
     * It then returns to the main customer screen and refreshes the table with the new customer added to the view.
     */
    @FXML void ClickSaveCust(ActionEvent event) throws SQLException, IOException {
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


    /**
     * Check if the form fields have been filled out; used for input validation.
     *
     * @return the boolean on successful validation
     */

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
            return true;
        }
    }

    /**
     * Checks the field lengths on text fields; used for input validation.
     *
     * @return the boolean on successful validation
     */
    public boolean checkLength() {
        int nameLength = NameField.getLength();
        int addrLength = AddrField.getLength();
        int phoneLength = PhoneField.getLength();
        int postLength = PostField.getLength();
        if ((nameLength > 50)||(addrLength > 100)||(phoneLength > 50)||(postLength > 50)) {
            if (nameLength > 50){
                NameField.setBorder(errorStyle);
                nameErr.setText("Maximum 50 characters; currently has " + nameLength + " characters.");
            }
            if (addrLength > 100){
                AddrField.setBorder(errorStyle);
                AddrErr.setText("Maximum 100 characters; currently has " + addrLength + " characters.");
            }
            if (phoneLength > 50){
                PhoneField.setBorder(errorStyle);
                PhoneErr.setText("Maximum 50 characters; currently has " + phoneLength + " characters.");
            }
            if (postLength > 50){
                PostField.setBorder(errorStyle);
                PostErr.setText("Maximum 50 characters; currently has " + postLength + " characters.");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Resets the form fields.
     */
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
