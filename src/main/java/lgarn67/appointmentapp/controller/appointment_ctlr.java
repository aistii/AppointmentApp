package lgarn67.appointmentapp.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lgarn67.appointmentapp.App;
import lgarn67.appointmentapp.Appointment;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class appointment_ctlr implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* Node monthTabCont = MonthApptTab.getContent();
        Node weekTabCont = WeekApptTab.getContent();
        MonthAppointmentTable = (TableView<Appointment>) monthTabCont.lookup("#MonthAppointmentTable");
        WeekAppointmentTable = (TableView<Appointment>) weekTabCont.lookup("#WeekAppointmentTable");*/
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now().format(AppointmentQuery.dtf));
       // System.out.println("Local Date Time of now: " + LocalDateTime.now());
        //System.out.println("Timestamp of now: " + ts);
        try {
            Working.resetAllAppointment();
            Working.resetMonthAppointment();
            Working.resetWeekAppointment();
            Working.resetCustomers();
            AppointmentQuery.selectAllAppt();
            AppointmentQuery.selectMonthAppt();
            AppointmentQuery.selectWeekAppt();
            CustomerQuery.selectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        AllAppointmentsTable.setItems(Working.getAllAppointments());
        AllAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        AllAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        AllAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        AllAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        AllAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        AllAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        AllAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        AllAppt_CustID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        AllAppt_UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        MonthAppointmentTable.setItems(Working.getMonthAppt());
        MonthAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MonthAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        MonthAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        MonthAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        MonthAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        MonthAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        MonthAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        MonthAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        MonthAppt_CustID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        MonthAppt_UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        WeekAppointmentTable.setItems(Working.getWeekAppt());
        WeekAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        WeekAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        WeekAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        WeekAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        WeekAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        WeekAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        WeekAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        WeekAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        WeekAppt_CustID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        WeekAppt_UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
    Stage stage;
    Parent scene;

    @FXML private Tab AllApptTab;
    @FXML private Tab MonthApptTab;
    @FXML private Tab WeekApptTab;
    @FXML private TabPane AppointmentTabPane;
    @FXML private Button ApptAddBtn;
    @FXML private Button ApptEditBtn;
    @FXML private Button ApptRemBtn;
    @FXML private Button SBApptBtn;
    @FXML private Button SBCustomerBtn;
    @FXML private Button SBExitBtn;
    @FXML private Button SBReportBtn;
    @FXML private TableView<Appointment> AllAppointmentsTable;
    @FXML private TableColumn<Appointment, String> AllAppt_Contact;
    @FXML private TableColumn<Appointment, Integer> AllAppt_CustID;
    @FXML private TableColumn<Appointment, String> AllAppt_Des;
    @FXML private TableColumn<Appointment, LocalDateTime> AllAppt_End;
    @FXML private TableColumn<Appointment, Integer> AllAppt_ID;
    @FXML private TableColumn<Appointment, String> AllAppt_Loc;
    @FXML private TableColumn<Appointment, LocalDateTime> AllAppt_Start;
    @FXML private TableColumn<Appointment, String> AllAppt_Title;
    @FXML private TableColumn<Appointment, String> AllAppt_Type;
    @FXML private TableColumn<Appointment, Integer> AllAppt_UserID;
    @FXML private TableView<Appointment> MonthAppointmentTable;
    @FXML private TableColumn<Appointment, String> MonthAppt_Contact;
    @FXML private TableColumn<Appointment, Integer> MonthAppt_CustID;
    @FXML private TableColumn<Appointment, String> MonthAppt_Des;
    @FXML private TableColumn<Appointment, LocalDateTime> MonthAppt_End;
    @FXML private TableColumn<Appointment, Integer> MonthAppt_ID;
    @FXML private TableColumn<Appointment, String> MonthAppt_Loc;
    @FXML private TableColumn<Appointment, LocalDateTime> MonthAppt_Start;
    @FXML private TableColumn<Appointment, String> MonthAppt_Title;
    @FXML private TableColumn<Appointment, String> MonthAppt_Type;
    @FXML private TableColumn<Appointment, Integer> MonthAppt_UserID;
    @FXML private TableView<Appointment> WeekAppointmentTable;
    @FXML private TableColumn<Appointment, String> WeekAppt_Contact;
    @FXML private TableColumn<Appointment, Integer> WeekAppt_CustID;
    @FXML private TableColumn<Appointment, Integer> WeekAppt_Des;
    @FXML private TableColumn<Appointment, LocalDateTime> WeekAppt_End;
    @FXML private TableColumn<Appointment, Integer> WeekAppt_ID;
    @FXML private TableColumn<Appointment, String> WeekAppt_Loc;
    @FXML private TableColumn<Appointment, LocalDateTime> WeekAppt_Start;
    @FXML private TableColumn<Appointment, String> WeekAppt_Title;
    @FXML private TableColumn<Appointment, String> WeekAppt_Type;
    @FXML private TableColumn<Appointment, Integer> WeekAppt_UserID;


    @FXML void clickAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addAppt.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void clickApptBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void clickCustBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void clickDelAppt(ActionEvent event) {

    }

    @FXML void clickEditAppt(ActionEvent event) {

    }

    @FXML void clickSBEXit(ActionEvent event) {
        Platform.exit();
    }


}
