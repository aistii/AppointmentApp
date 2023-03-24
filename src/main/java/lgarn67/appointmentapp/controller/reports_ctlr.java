package lgarn67.appointmentapp.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lgarn67.appointmentapp.dao.ContactQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.ReportsQuery;
import lgarn67.appointmentapp.dao.UserQuery;
import lgarn67.appointmentapp.helper.TimeChecks;
import lgarn67.appointmentapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the reports view.
 */
public class reports_ctlr implements Initializable {

    /**
     * <p>Initializes by resetting preexisting data within the contacts, country-FLD-count, and month-type-count report Observable lists and then does the query for them.
     * The contact combo is set with a list of contacts; whichever is selected will populate the contact's schedule table with the matching data.</p>
     * <p>Also sets the values to be used in the TableViews and columns.</p>
     * <p>The lambdas here are used on the sidebar buttons to change scenes once clicked. There is also a lambda used for the TableView column to only display the appointment's associated customer's ID.</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Working.resetContacts();
            Working.resetFLDRep();
            Working.resetMTRep();
            ReportsQuery.qFLDCountRep();
            ReportsQuery.qMTCountRep();
            contSche.clear();
            ContactQuery.selectAllContacts();
            ContactCombo.setItems(Working.getAllContacts());

        } catch (Exception e) {

        }
        ContactCombo.setItems(Working.getAllContacts());
        ContactSchTable.setItems(contSche);

        // Lambda for setting the sidebar appointment button to go to that scene.
        SBApptBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
            } catch (IOException ioException) {
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        // Lambda for setting the sidebar customer button to go to that scene.
        SBCustomerBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
            } catch (IOException ioException) {
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        // Lambda for setting the sidebar report button to go to that scene.
        SBReportBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/reportsView.fxml"));
            } catch (IOException ioException) {
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        // Lambda for setting the sidebar exit button to go to exit the app.
        SBExitBtn.setOnAction(e -> Platform.exit());

        ContactSchTable.setItems(null);
        Contact_ApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        Contact_TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        Contact_CustIDCol.setCellValueFactory(cust -> new SimpleStringProperty(Integer.toString(cust.getValue().getCustomer().getId())));
        Contact_DescCol.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        Contact_StartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        Contact_EndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        Contact_TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        CustFLDTable.setItems(Working.getFldReport());
        CustFLD_CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustFLD_FLDCol.setCellValueFactory(new PropertyValueFactory<>("fld"));
        CustFLD_NumCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        MonthTypeTable.setItems(Working.getMtReport());
        MT_CountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        MT_MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        MT_TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    /**
     * The Stage/window.
     */
    Stage stage;
    /**
     * The Scene/view.
     */
    Parent scene;
    /**
     * The contact combo box to choose from.
     */
    @FXML private ComboBox<Contact> ContactCombo;
    /**
     * The contact schedule report TableView.
     */
    @FXML private TableView<Appointment> ContactSchTable;
    /**
     * Column in ContactSchTable for appointment ID.
     */
    @FXML private TableColumn<Appointment, Integer> Contact_ApptIDCol;
    /**
     * Column in ContactSchTable for customer ID.
     */
    @FXML private TableColumn<Appointment, String> Contact_CustIDCol;
    /**
     * Column in ContactSchTable for appointment description.
     */
    @FXML private TableColumn<Appointment, String> Contact_DescCol;
    /**
     * Column in ContactSchTable for appointment end date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> Contact_EndCol;
    /**
     * Column in ContactSchTable for appointment start date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> Contact_StartCol;
    /**
     * Column in ContactSchTable for appointment title.
     */
    @FXML private TableColumn<Appointment, String> Contact_TitleCol;
    /**
     * Column in ContactSchTable for appointment type.
     */
    @FXML private TableColumn<Appointment, String> Contact_TypeCol;
    /**
     * The country-FLD-count TableView to display how many customers are within a certain division.
     */
    @FXML private TableView<FLDReport> CustFLDTable;
    /**
     * Column in CustFLDTable for country name.
     */
    @FXML private TableColumn<FLDReport, String> CustFLD_CountryCol;
    /**
     * Column in CustFLDTable for first-level division name.
     */
    @FXML private TableColumn<FLDReport, String> CustFLD_FLDCol;
    /**
     * Column in CustFLDTable for the number of customers within that division.
     */
    @FXML private TableColumn<FLDReport, Integer> CustFLD_NumCol;
    /**
     * The month-type-count TableView to display how many appointments were scheduled within a certain month and of a certain type.
     */
    @FXML private TableView<MonthTypeReport> MonthTypeTable;
    /**
     * Column in MonthTypeTable for number of appointments
     */
    @FXML private TableColumn<MonthTypeReport, Integer> MT_CountCol;
    /**
     * Column in MonthTypeTable for month name
     */
    @FXML private TableColumn<MonthTypeReport, String> MT_MonthCol;
    /**
     * Column in MonthTypeTable for type
     */
    @FXML private TableColumn<MonthTypeReport, String> MT_TypeCol;
    /**
     * The sidebar's button to switch to "appointment" view.
     */
    @FXML private Button SBApptBtn;
    /**
     * The sidebar's button to switch to "customer" view.
     */
    @FXML private Button SBCustomerBtn;
    /**
     * The sidebar's button to close the app.
     */
    @FXML private Button SBExitBtn;
    /**
     * The sidebar's button to switch to "report" view.
     */
    @FXML private Button SBReportBtn;
    /**
     * Observable List of the currently selected contact's schedule of appointments.
     */
    static ObservableList<Appointment> contSche = FXCollections.observableArrayList();


    /**
     * Populates the ObservableList contSche with the currently selected contact's associated appointments, determined from the selection in the combo box
     */
    void popuContScheOL() throws SQLException {
        contSche.clear();
        if (!ContactCombo.getSelectionModel().isEmpty()){
            int contId = ContactCombo.getSelectionModel().getSelectedItem().getId();
            ResultSet rs = ReportsQuery.qContactSchedule(contId);
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String descrip = rs.getString("Description");
                String loc = rs.getString("Location");
                Contact cont = ContactQuery.contactFetch(contId);
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toInstant().atZone(TimeChecks.getLocalZoneId()).toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                Customer cust = CustomerQuery.selectCustomer(custId);
                int userId = rs.getInt("User_ID");
                User user = UserQuery.userFetch(userId);
                contSche.add(new Appointment(id, title, descrip, loc, cont, type, startDateTime, endDateTime, cust, user));
            }
        }

    }

    /**
     * Fills the contSche ObservableList through popuContScheOl, then sets the contact schedule table with the Observable List
     */
    @FXML void onContactSelected(ActionEvent event) throws SQLException {
        popuContScheOL();
        ContactSchTable.setItems(contSche);
    }

    /**
     * Refreshes the data on the reports page.
     */
    @FXML void onClickRefresh(ActionEvent event) {
        ContactCombo.getSelectionModel().clearSelection();
        ContactCombo.setValue(null);
        if (ContactCombo.getSelectionModel().isEmpty() || ContactCombo.getValue() == null){
            ContactCombo.getPromptText();
        }
        try {
            Working.resetContacts();
            Working.resetFLDRep();
            Working.resetMTRep();
            ReportsQuery.qFLDCountRep();
            ReportsQuery.qMTCountRep();
            contSche.clear();
            ContactQuery.selectAllContacts();
            ContactCombo.setItems(Working.getAllContacts());
        } catch (Exception e) {

        }

    }

}
