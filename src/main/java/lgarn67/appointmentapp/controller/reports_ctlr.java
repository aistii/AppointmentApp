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

public class reports_ctlr implements Initializable {

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

        SBApptBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });

        SBCustomerBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        SBReportBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/reportsView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        SBExitBtn.setOnAction(e -> Platform.exit());



        // Table View Initialize
        // --- Schedule table uses appointment class's attributes
        ContactSchTable.setItems(null);
        Contact_ApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        Contact_TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        Contact_CustIDCol.setCellValueFactory(cust -> new SimpleStringProperty(Integer.toString(cust.getValue().getCustomer().getId())));
        Contact_DescCol.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        Contact_StartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        Contact_EndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        Contact_TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        // --- FLD Count Table
        CustFLDTable.setItems(Working.getFldReport());
        CustFLD_CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustFLD_FLDCol.setCellValueFactory(new PropertyValueFactory<>("fld"));
        CustFLD_NumCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        // --- Month/Type Count Table
        MonthTypeTable.setItems(Working.getMtReport());
        MT_CountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        MT_MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        MT_TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    }
    Stage stage;
    Parent scene;
    @FXML ComboBox<Contact> ContactCombo;
    @FXML TableView<Appointment> ContactSchTable;
    @FXML TableColumn<Appointment, Integer> Contact_ApptIDCol;
    @FXML TableColumn<Appointment, String> Contact_CustIDCol;
    @FXML TableColumn<Appointment, String> Contact_DescCol;
    @FXML TableColumn<Appointment, LocalDateTime> Contact_EndCol;
    @FXML TableColumn<Appointment, LocalDateTime> Contact_StartCol;
    @FXML TableColumn<Appointment, String> Contact_TitleCol;
    @FXML private TableColumn<Appointment, String> Contact_TypeCol;
    @FXML private TableView<FLDReport> CustFLDTable;
    @FXML private TableColumn<FLDReport, String> CustFLD_CountryCol;
    @FXML private TableColumn<FLDReport, String> CustFLD_FLDCol;
    @FXML private TableColumn<FLDReport, Integer> CustFLD_NumCol;
    @FXML private TableView<MonthTypeReport> MonthTypeTable;
    @FXML private TableColumn<MonthTypeReport, Integer> MT_CountCol;
    @FXML private TableColumn<MonthTypeReport, String> MT_MonthCol;
    @FXML private TableColumn<MonthTypeReport, String> MT_TypeCol;
    @FXML private Button RefreshButton;
    @FXML private Button SBApptBtn;
    @FXML private Button SBCustomerBtn;
    @FXML private Button SBExitBtn;
    @FXML private Button SBReportBtn;
    static ObservableList<Appointment> contSche = FXCollections.observableArrayList();


    // popContScheOL only populates the ObservableList to be used by the ContactSchTable.
    // will invoke the table population elsewhere
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
                Contact cont = ContactQuery.contactFetch(contId); // it's the same number, so why not
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
    @FXML void onContactSelected(ActionEvent event) throws SQLException {
        // no need to clear the table since its observable list is cleared in popuContScheOL
        popuContScheOL();
        ContactSchTable.setItems(contSche);
    }

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
