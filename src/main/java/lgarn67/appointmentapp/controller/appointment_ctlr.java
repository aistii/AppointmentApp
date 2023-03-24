package lgarn67.appointmentapp.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.model.Appointment;
import lgarn67.appointmentapp.model.Working;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the main appointment screen.
 * This has the table for all appointments and the ones for the current month and week tables.
 * It includes the add, edit, and delete appointment buttons.
 */
public class appointment_ctlr implements Initializable {
    /**
     * <p>Initializes the scene and populates the tables.</p>
     * <p>There are several lambdas in play here.</p>
     * <ul>
     *     <li>The "SimpleStringProperty" lambdas that are used with the table columns are pull:
     *      <ul>
     *         <li>The strings for the Contact names in each of the three tables</li>
     *         <li>The conversion of the integers of customer ID and user ID to a string</li>
     *         <li>This was done to keep the action in a single line without having to create extra external methods to pull data from each of the object.</li>
     *     </ul>
     *     </li>
     *     <li>The "setOnAction" methods for the add and sidebar buttons simply use a lambda in place of having to call another method to invoke when the button is pressed.</li>
     * </ul>
     * <p>This Stack Overflow link helped me with determining how to set the values for the columns with lambdas:
     * https://stackoverflow.com/questions/43216716/javafx-how-to-set-string-values-to-tableview</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        }

        AllAppointmentsTable.setItems(Working.getAllAppointments());
        AllAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        AllAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        AllAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        // lambda to pull the contact's name from the contact object stored in the column.
        AllAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        AllAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        AllAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        AllAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        // lambda to pull the customer's ID from the customer object stored in the column, and cast it into a string.
        AllAppt_CustID.setCellValueFactory(cust -> new SimpleStringProperty(Integer.toString(cust.getValue().getCustomer().getId())));
        // lambda to pull the user's ID from the user object stored in the column, and cast it into a string.
        AllAppt_UserID.setCellValueFactory(user -> new SimpleStringProperty(Integer.toString(user.getValue().getUser().getId())));

        MonthAppointmentTable.setItems(Working.getMonthAppt());
        MonthAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MonthAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        MonthAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        MonthAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        // lambda to pull the contact's name from the contact object stored in the column.
        MonthAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        MonthAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        MonthAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        MonthAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        // lambda to pull the customer's ID from the customer object stored in the column, and cast it into a string.
        MonthAppt_CustID.setCellValueFactory(cust -> new SimpleStringProperty(Integer.toString(cust.getValue().getCustomer().getId())));
        // lambda to pull the user's ID from the user object stored in the column, and cast it into a string.
        MonthAppt_UserID.setCellValueFactory(user -> new SimpleStringProperty(Integer.toString(user.getValue().getUser().getId())));

        WeekAppointmentTable.setItems(Working.getWeekAppt());
        WeekAppt_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        WeekAppt_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        WeekAppt_Des.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        WeekAppt_Loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        // lambda to pull the contact's name from the contact object stored in the column.
        WeekAppt_Contact.setCellValueFactory(contName -> new SimpleStringProperty(contName.getValue().getContact().getName()));
        WeekAppt_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        WeekAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        WeekAppt_End.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        // lambda to pull the customer's ID from the customer object stored in the column, and cast it into a string.
        WeekAppt_CustID.setCellValueFactory(cust -> new SimpleStringProperty(Integer.toString(cust.getValue().getCustomer().getId())));
        // lambda to pull the user's ID from the user object stored in the column, and cast it into a string.
        WeekAppt_UserID.setCellValueFactory(user -> new SimpleStringProperty(Integer.toString(user.getValue().getUser().getId())));

        // Lambda for setting the add appointment button to go to that scene.
        ApptAddBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addAppt.fxml"));
            } catch (IOException ioException) {

            }
            stage.setScene(new Scene(scene));
            stage.show();
        });

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
     * The tab to view all appointments
     */
    @FXML private Tab AllApptTab;
    /**
     * The tab to view appointments for the current month.
     */
    @FXML private Tab MonthApptTab;
    /**
     * The tab to view appointments for the current week.
     */
    @FXML private Tab WeekApptTab;
    /**
     * The tabpane to hold the AllApptTab, MonthApptTab, and WeekApptTab.
     */
    @FXML private TabPane AppointmentTabPane;
    /**
     * The add appointment button.
     */
    @FXML private Button ApptAddBtn;
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
     * The TableView showing all appointments from the database.
     */
    @FXML private TableView<Appointment> AllAppointmentsTable;
    /**
     * Column in AllAppointmentsTable that holds contact name.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_Contact;
    /**
     * Column in AllAppointmentsTable that holds customer ID.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_CustID;
    /**
     * Column in AllAppointmentsTable that holds appointment description.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_Des;
    /**
     * Column in AllAppointmentsTable that holds appointment's end date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> AllAppt_End;
    /**
     * Column in AllAppointmentsTable that holds appointment ID.
     */
    @FXML private TableColumn<Appointment, Integer> AllAppt_ID;
    /**
     * Column in AllAppointmentsTable that holds appointment's location.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_Loc;
    /**
     * Column in AllAppointmentsTable that holds appointment's start date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> AllAppt_Start;
    /**
     * Column in AllAppointmentsTable that holds appointment's title.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_Title;
    /**
     * Column in AllAppointmentsTable that holds appointment's type.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_Type;
    /**
     * Column in AllAppointmentsTable that holds appointment user ID.
     */
    @FXML private TableColumn<Appointment, String> AllAppt_UserID;
    /**
     * The TableView showing all appointments of the current month from the database.
     */
    @FXML private TableView<Appointment> MonthAppointmentTable;
    /**
     * Column in AllAppointmentsTable that holds contact name.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_Contact;
    /**
     * Column in MonthAppointmentTable that holds customer ID.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_CustID;
    /**
     * Column in MonthAppointmentTable that holds appointment description.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_Des;
    /**
     * Column in MonthAppointmentTable that holds appointment's end date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> MonthAppt_End;
    /**
     * Column in MonthAppointmentTable that holds appointment ID.
     */
    @FXML private TableColumn<Appointment, Integer> MonthAppt_ID;
    /**
     * Column in MonthAppointmentTable that holds appointment's location.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_Loc;
    /**
     * Column in MonthAppointmentTable that holds appointment's start date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> MonthAppt_Start;
    /**
     * Column in MonthAppointmentTable that holds appointment's title.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_Title;
    /**
     * Column in MonthAppointmentTable that holds appointment's type.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_Type;
    /**
     * Column in MonthAppointmentTable that holds appointment user ID.
     */
    @FXML private TableColumn<Appointment, String> MonthAppt_UserID;
    /**
     * The TableView showing all appointments of the current week from the database.
     */
    @FXML private TableView<Appointment> WeekAppointmentTable;
    /**
     * Column in WeekAppointmentTable that holds contact name.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_Contact;
    /**
     * Column in WeekAppointmentTable that holds customer ID.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_CustID;
    /**
     * Column in WeekAppointmentTable that holds appointment description.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_Des;
    /**
     * Column in WeekAppointmentTable that holds appointment's end date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> WeekAppt_End;
    /**
     * Column in WeekAppointmentTable that holds appointment ID.
     */
    @FXML private TableColumn<Appointment, Integer> WeekAppt_ID;
    /**
     * Column in WeekAppointmentTable that holds appointment's location.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_Loc;
    /**
     * Column in WeekAppointmentTable that holds appointment's start date-time.
     */
    @FXML private TableColumn<Appointment, LocalDateTime> WeekAppt_Start;
    /**
     * Column in WeekAppointmentTable that holds appointment's title.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_Title;
    /**
     * Column in WeekAppointmentTable that holds appointment's type.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_Type;
    /**
     * Column in WeekAppointmentTable that holds appointment user ID.
     */
    @FXML private TableColumn<Appointment, String> WeekAppt_UserID;


    /**
     *
     * <p>Deletes the selected appointment, if there is an appointment that is selected.
     * It will pull the ID of the selected appointment from whichever tab it is currently in.
     * Once deleted, it will clear the Observable Lists in the Working class and then repopulate them.
     * Error message will show up if no appointment is selected before pushing delete.</p>
     */
    @FXML void clickDelAppt(ActionEvent event) throws SQLException {
        try {
            if (AppointmentTabPane.getSelectionModel().getSelectedItem() == AllApptTab) {
                Appointment selA = AllAppointmentsTable.getSelectionModel().getSelectedItem();
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                alert_ctlr.loadDialogStyle(confirm);
                confirm.setTitle("Confirm Appointment Deletion");
                confirm.setHeaderText("Confirm Appointment Deletion");
                confirm.setContentText("Canceling appointment ID #" +selA.getId()+ " of type " + selA.getType() + ". Continue?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    AppointmentQuery.deleteAppointment(selA.getId());
                    Working.resetAllAppointment();
                    Working.resetMonthAppointment();
                    Working.resetWeekAppointment();
                    Working.resetCustomers();
                    AppointmentQuery.selectAllAppt();
                    AppointmentQuery.selectMonthAppt();
                    AppointmentQuery.selectWeekAppt();
                    CustomerQuery.selectAll();
                    Alert executed = new Alert(Alert.AlertType.INFORMATION, "Appointment ID #" +selA.getId()+
                            " of type "+selA.getType()+ " has been cancelled.");
                    alert_ctlr.loadDialogStyle(executed);
                    executed.showAndWait();
                }

            } else if (AppointmentTabPane.getSelectionModel().getSelectedItem() == MonthApptTab) {
                Appointment selA = MonthAppointmentTable.getSelectionModel().getSelectedItem();
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                alert_ctlr.loadDialogStyle(confirm);
                confirm.setTitle("Confirm Customer Deletion");
                confirm.setHeaderText("Confirm Customer Deletion");
                confirm.setContentText("Canceling appointment ID #" +selA.getId()+ " of type " + selA.getType() + ". Continue?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    AppointmentQuery.deleteAppointment(selA.getId());
                    Working.resetAllAppointment();
                    Working.resetMonthAppointment();
                    Working.resetWeekAppointment();
                    Working.resetCustomers();
                    AppointmentQuery.selectAllAppt();
                    AppointmentQuery.selectMonthAppt();
                    AppointmentQuery.selectWeekAppt();
                    CustomerQuery.selectAll();
                    Alert executed = new Alert(Alert.AlertType.INFORMATION, "Appointment ID #" +selA.getId()+
                            " of type "+selA.getType()+ " has been cancelled.");
                    alert_ctlr.loadDialogStyle(executed);
                    executed.showAndWait();
                }

            } else if (AppointmentTabPane.getSelectionModel().getSelectedItem() == WeekApptTab) {
                Appointment selA = WeekAppointmentTable.getSelectionModel().getSelectedItem();
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                alert_ctlr.loadDialogStyle(confirm);
                confirm.setTitle("Confirm Customer Deletion");
                confirm.setHeaderText("Confirm Customer Deletion");
                confirm.setContentText("Canceling appointment ID #" +selA.getId()+ " of type " + selA.getType() + ". Continue?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && (result.get() == ButtonType.OK)) {
                    AppointmentQuery.deleteAppointment(selA.getId());
                    Working.resetAllAppointment();
                    Working.resetMonthAppointment();
                    Working.resetWeekAppointment();
                    Working.resetCustomers();
                    AppointmentQuery.selectAllAppt();
                    AppointmentQuery.selectMonthAppt();
                    AppointmentQuery.selectWeekAppt();
                    CustomerQuery.selectAll();
                    Alert executed = new Alert(Alert.AlertType.INFORMATION, "Appointment ID #" +selA.getId()+
                            " of type "+selA.getType()+ " has been cancelled.");
                    alert_ctlr.loadDialogStyle(executed);
                    executed.showAndWait();
                }
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert_ctlr.loadDialogStyle(alert);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment to cancel.");
            alert.showAndWait();
        }
    }

    /**
     * Gets the ID of the selected appointment, if any is selected. The ID is used to query the database which is then sent to the edit appointment controller.
     * If no appointment is selected prior to pressing "edit", it will have an error message notifying the user.
     */
    @FXML void clickEditAppt(ActionEvent event) throws IOException{
        try {
            if ((AppointmentTabPane.getSelectionModel().getSelectedItem() == AllApptTab)) {
                Appointment selA = AllAppointmentsTable.getSelectionModel().getSelectedItem();
                edit_appt_ctlr.loadData(AppointmentQuery.selectAppt(selA.getId()));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/editAppt.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if ((AppointmentTabPane.getSelectionModel().getSelectedItem() == MonthApptTab)) {
                Appointment selA = MonthAppointmentTable.getSelectionModel().getSelectedItem();
                edit_appt_ctlr.loadData(AppointmentQuery.selectAppt(selA.getId()));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/editAppt.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if ((AppointmentTabPane.getSelectionModel().getSelectedItem() == WeekApptTab)) {
                Appointment selA = WeekAppointmentTable.getSelectionModel().getSelectedItem();
                edit_appt_ctlr.loadData(AppointmentQuery.selectAppt(selA.getId()));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/editAppt.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (NullPointerException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert_ctlr.loadDialogStyle(alert);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment to edit.");
            alert.showAndWait();
        }
    }
}
