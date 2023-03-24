package lgarn67.appointmentapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lgarn67.appointmentapp.helper.TimeChecks;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.ContactQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.UserQuery;
import lgarn67.appointmentapp.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for the edit appointment view.
 */
public class edit_appt_ctlr implements Initializable {
    /**
     * Fetches the list of contacts, users, and customers;
     * sets the time zone label to reflect the one the user is in;
     * converts the date in datepicker to desired format.
     * Clears the spinners before input is added. Additionally loads the selected appointments details in.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Working.resetContacts();
            Working.resetUsers();
            Working.resetCustomers();
            ContactQuery.selectAllContacts();
            UserQuery.selectAllUsers();
            CustomerQuery.selectAll();
        } catch (SQLException e) {
        }

        timeZoneLabel.setText(String.valueOf(TimeChecks.getLocalZoneId()));
        startDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormat.format(localDate);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return LocalDate.parse(s, dateFormat);
                } else {
                    return null;
                }
            }
        });
        endDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormat.format(localDate);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return LocalDate.parse(s, dateFormat);
                } else {
                    return null;
                }
            }
        });

        custField.setItems(Working.getAllCustomers());
        contactField.setItems(Working.getAllContacts());
        userField.setItems(Working.getAllUsers());
        idField.setText(Integer.toString(loadedId));
        titleField.setText(loadedTitle);
        locField.setText(loadedLoc);
        descField.setText(loadedDesc);
        typeField.setText(loadedType);
        contactField.getSelectionModel().select(loadedCont);
        custField.getSelectionModel().select(loadedCust);
        userField.getSelectionModel().select(loadedUser);
        startDate.setValue(loadedStart.toLocalDate());
        startHour.getValueFactory().setValue(loadedStart.toLocalTime().getHour());
        startMin.getValueFactory().setValue(loadedStart.toLocalTime().getMinute());
        endDate.setValue(loadedEnd.toLocalDate());
        endHour.getValueFactory().setValue(loadedEnd.toLocalTime().getHour());
        endMinute.getValueFactory().setValue(loadedEnd.toLocalTime().getMinute());
    }

    /**
     * The Stage/stage.
     */
    Stage stage;
    /**
     * The Scene/scene.
     */
    Parent scene;
    /**
     * The appointment ID field.
     */
    @FXML private TextField idField;
    /**
     * The cancel button.
     */
    @FXML private Button CancelBtn;
    /**
     * The save button.
     */
    @FXML private Button SaveBtn;
    /**
     * The contact error message label.
     */
    @FXML private Label contErrMsg;
    /**
     * The contact combo box.
     */
    @FXML private ComboBox<Contact> contactField;
    /**
     * The customer error message label.
     */
    @FXML private Label custErrMsg;
    /**
     * The customer combo box.
     */
    @FXML private ComboBox<Customer> custField;
    /**
     * The description error message label.
     */
    @FXML private Label descErrMsg;
    /**
     * The description textarea.
     */
    @FXML private TextArea descField;
    /**
     * The datepicker for the end date.
     */
    @FXML private DatePicker endDate;
    /**
     * The end date error message label.
     */
    @FXML private Label endErrMsg;
    /**
     * The spinner for the ending hour.
     */
    @FXML private Spinner<Integer> endHour;
    /**
     * The spinner for the ending minute.
     */
    @FXML private Spinner<Integer> endMinute;
    /**
     * The location error message label.
     */
    @FXML private Label locErrMsg;
    /**
     * The location text field.
     */
    @FXML private TextField locField;
    /**
     * The datepicker for the start date.
     */
    @FXML private DatePicker startDate;
    /**
     * The start date error message label.
     */
    @FXML private Label startErrMsg;
    /**
     * The spinner for the starting hour.
     */
    @FXML private Spinner<Integer> startHour;
    /**
     * The spinner for the starting minute.
     */
    @FXML private Spinner<Integer> startMin;
    /**
     * The title error message label.
     */
    @FXML private Label titleErrMsg;
    /**
     * The title text field.
     */
    @FXML private TextField titleField;
    /**
     * The type error message label.
     */
    @FXML private Label typeErrMsg;
    /**
     * The type text field.
     */
    @FXML private TextField typeField;
    /**
     * The user error message.
     */
    @FXML private Label userErrMsg;
    /**
     * The user combo box.
     */
    @FXML private ComboBox<User> userField;
    /**
     * The time label to display user's local time zone.
     */
    @FXML private Label timeZoneLabel;
    /**
     * The border style used to denote that a field has an error.
     */
    Border errorStyle = new Border(new BorderStroke(Color.valueOf("#EF596F"), BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
    /**
     * The "default" (errorless) style used on fields if errorStyle had been applied prior.
     */
    Border clearStyle = new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.NONE, null, new BorderWidths(0)));
    /**
     * Formatter for the datepickers.
     */
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * The loaded appointment's id.
     */
    static int loadedId;
    /**
     * The loaded appointment's title.
     */
    static String loadedTitle;
    /**
     * The loaded appointment's description.
     */
    static String loadedDesc;
    /**
     * The loaded appointment's location.
     */
    static String loadedLoc;
    /**
     * The loaded appointment's type.
     */
    static String loadedType;
    /**
     * The loaded appointment's start date-time.
     */
    static LocalDateTime loadedStart;
    /**
     * The loaded appointment's end date-time.
     */
    static LocalDateTime loadedEnd;
    /**
     * The loaded appointment's customer.
     */
    static Customer loadedCust;
    /**
     * The loaded appointment's user.
     */
    static User loadedUser;
    /**
     * The loaded appointment's contact.
     */
    static Contact loadedCont;

    /**
     * Runs validation checks on the fields and against the business hours.
     * If successful, it modifies the appointment in the database and returns to the appointment screen, with the updated appointment.
     */
    @FXML void clickSaveAppt (ActionEvent event) throws SQLException {
        if (checkCompletion()){
            if (checkLength()) {
                try {
                    LocalDate apptStartDate = LocalDate.parse(startDate.getEditor().getText(), dateFormat);
                    LocalDate apptEndDate = LocalDate.parse(endDate.getEditor().getText(), dateFormat);
                    try {
                        int apptStartHr = Integer.parseInt(startHour.getEditor().getText());
                        int apptStartMin = Integer.parseInt(startMin.getEditor().getText());
                        int apptEndHr = Integer.parseInt(endHour.getEditor().getText());
                        int apptEndMin = Integer.parseInt(endMinute.getEditor().getText());

                        LocalDateTime userStartLDT = LocalDateTime.of(apptStartDate, LocalTime.of(apptStartHr, apptStartMin));
                        ZonedDateTime userStartZDT = ZonedDateTime.of(userStartLDT, TimeChecks.getLocalZoneId());
                        ZonedDateTime testUserStartEastern = userStartZDT.withZoneSameInstant(TimeChecks.getEastZoneId());

                        LocalDateTime userEndLDT = LocalDateTime.of(apptEndDate, LocalTime.of(apptEndHr, apptEndMin));
                        ZonedDateTime userEndZDT = ZonedDateTime.of(userEndLDT, TimeChecks.getLocalZoneId());
                        ZonedDateTime testUserEndEastern = userEndZDT.withZoneSameInstant(TimeChecks.getEastZoneId());

                        if (TimeChecks.checkStartingTime(testUserStartEastern)) {
                            if (TimeChecks.checkEndingTime(testUserStartEastern, testUserEndEastern)) {
                                int custId = custField.getSelectionModel().getSelectedItem().getId();
                                int apptId = Integer.parseInt(idField.getText());
                                if ((TimeChecks.checkOverlap(userStartZDT, userEndZDT, custId, apptId))) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert_ctlr.loadDialogStyle(alert);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Appointment Conflict (Overlapping Time)");
                                    alert.setContentText("There is already an appointment within this timeframe. Please pick a different time.");
                                    alert.showAndWait();
                                } else {
                                    try {
                                        String title = titleField.getText();
                                        String desc = descField.getText();
                                        String loc = locField.getText();
                                        String type = typeField.getText();
                                        Instant startDt = userStartZDT.toInstant();
                                        Instant endDt = userEndZDT.toInstant();
                                        int userId = userField.getSelectionModel().getSelectedItem().getId();
                                        int contId = contactField.getSelectionModel().getSelectedItem().getId();
                                        AppointmentQuery.updateAppointment(apptId, title, desc, loc, type, startDt, endDt, custId, userId, contId);
                                        resetFields();
                                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
                                        stage.setScene(new Scene(scene));
                                        stage.show();
                                    } catch (Exception e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert_ctlr.loadDialogStyle(alert);
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Error Saving Appointment");
                                        alert.setContentText("Please ensure data is inputted correctly.");
                                        alert.showAndWait();
                                    }
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert_ctlr.loadDialogStyle(alert);
                                alert.setTitle("Error");
                                alert.setHeaderText("Appointment Time Error");
                                alert.setContentText("Please ensure appointment end time is after the start time and before 10pm eastern time.");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert_ctlr.loadDialogStyle(alert);
                            alert.setTitle("Error");
                            alert.setHeaderText("Appointment Time Error");
                            alert.setContentText("Please ensure appointment start time is after 8am eastern time.");
                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        startErrMsg.setText("Please ensure the time values are numerical.");
                        endErrMsg.setText("Please ensure the time values are numerical.");
                    }
                } catch (Exception e) {
                    startErrMsg.setText("Please ensure date is correctly typed in.");
                }
            }
        }
    }

    /**
     * Loads appointment data.
     *
     * @param selA the selected appointment from the main appointment screen.
     */
    public static void loadData (Appointment selA) {
        loadedId = selA.getId();
        loadedTitle = selA.getTitle();
        loadedDesc = selA.getDescrip();
        loadedLoc = selA.getLocation();
        loadedType = selA.getType();
        loadedStart = selA.getStartDateTime();
        loadedEnd = selA.getEndDateTime();
        loadedCust = selA.getCustomer();
        loadedUser = selA.getUser();
        loadedCont = selA.getContact();
    }

    /**
     * Exits the edit appointment function without saving.
     */
    @FXML void clickCancel(ActionEvent event) throws IOException {
        resetFields();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks the field lengths on text field/area; used for input validation.
     *
     * @return the boolean on successful validation
     */
    public boolean checkLength () {
        int titleLength = titleField.getLength();
        int locLength = locField.getLength();
        int descLength = descField.getLength();
        int typeLength = typeField.getLength();

        if ((titleLength > 50) || (locLength > 50) || (descLength > 50) || (typeLength > 50)) {
            if (titleLength > 50) {
                titleField.setBorder(errorStyle);
                titleErrMsg.setText("Maximum 50 characters; currently has " + titleLength + " characters.");
            }
            if (locLength > 50) {
                locField.setBorder(errorStyle);
                locErrMsg.setText("Maximum 50 characters; currently has " + locLength + " characters.");
            }
            if (descLength > 50) {
                descField.setBorder(errorStyle);
                descErrMsg.setText("Maximum 50 characters; currently has " + descLength + " characters.");
            }
            if (typeLength > 50) {
                typeField.setBorder(errorStyle);
                typeErrMsg.setText("Maximum 50 characters; currently has " + typeLength + " characters.");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the form fields have been filled out; used for input validation.
     *
     * @return the boolean on successful validation
     */
    public boolean checkCompletion () {
        resetErrorMsg();

        if (titleField.getText().isBlank() || locField.getText().isBlank() || descField.getText().isBlank() ||
                typeField.getText().isBlank() || contactField.getValue()==null || custField.getValue()==null ||
                userField.getValue()==null || startDate.getEditor().getText().isEmpty() || endDate.getEditor().getText().isEmpty() ||
                startHour.getValue()==null || startMin.getValue()==null || endHour.getValue()==null || endMinute.getValue()==null) {

            if (titleField.getText().isBlank()) {
                titleField.setBorder(errorStyle);
                titleErrMsg.setText("Please enter a title.");
            }
            if (locField.getText().isBlank()) {
                locField.setBorder(errorStyle);
                locErrMsg.setText("Please enter a location.");
            }
            if (descField.getText().isBlank()) {
                descField.setBorder(errorStyle);
                descErrMsg.setText("Please enter a description.");
            }
            if (typeField.getText().isBlank()) {
                typeField.setBorder(errorStyle);
                typeErrMsg.setText("Please enter an appointment type.");
            }
            if (contactField.getValue()==null) {
                contactField.setBorder(errorStyle);
                contErrMsg.setText("Please select a contact.");
            }
            if (custField.getValue()==null) {
                custField.setBorder(errorStyle);
                custErrMsg.setText("Please select a customer.");
            }
            if (userField.getValue()==null) {
                userField.setBorder(errorStyle);
                userErrMsg.setText("Please select a user.");
            }
            try {
                if (startDate.getEditor().getText().isEmpty() || startHour.getValue()==null || startMin.getValue()==null) {
                    startDate.setBorder(errorStyle);
                    startHour.setBorder(errorStyle);
                    startMin.setBorder(errorStyle);
                    startErrMsg.setText("Please enter a start date and time.");
                }
            } catch (NullPointerException e) {
                startDate.setBorder(errorStyle);
                startHour.setBorder(errorStyle);
                startMin.setBorder(errorStyle);
                startErrMsg.setText("Please enter a start date and time.");
            }
            try {
                if (endDate.getEditor().getText().isEmpty()|| endHour.getValue()==null || endMinute.getValue()==null) {
                    endDate.setBorder(errorStyle);
                    endHour.setBorder(errorStyle);
                    endMinute.setBorder(errorStyle);
                    endErrMsg.setText("Please enter an end date and time.");
                }
            } catch (Exception e) {
                endDate.setBorder(errorStyle);
                endHour.setBorder(errorStyle);
                endMinute.setBorder(errorStyle);
                endErrMsg.setText("Please enter an end date and time.");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Resets the form fields.
     */
    public void resetFields() {
        titleField.clear();
        locField.clear();
        descField.clear();
        typeField.clear();
        contactField.getSelectionModel().select(null);
        custField.getSelectionModel().select(null);
        userField.getSelectionModel().select(null);
        startDate.getEditor().clear();
        endDate.getEditor().clear();
        startHour.getEditor().clear();
        startMin.getEditor().clear();
        endHour.getEditor().clear();
        endMinute.getEditor().clear();
        Working.resetCustomers();
        Working.resetUsers();
        Working.resetContacts();
        resetErrorMsg();
    }

    /**
     * Resets the errorStyle border on the fields, and removes the error message in the error labels.
     */
    public void resetErrorMsg() {
        titleErrMsg.setText(null);
        locErrMsg.setText(null);
        descErrMsg.setText(null);
        typeErrMsg.setText(null);
        contErrMsg.setText(null);
        custErrMsg.setText(null);
        userErrMsg.setText(null);
        startErrMsg.setText(null);
        endErrMsg.setText(null);
        titleField.setBorder(clearStyle);
        locField.setBorder(clearStyle);
        descField.setBorder(clearStyle);
        typeField.setBorder(clearStyle);
        contactField.setBorder(clearStyle);
        custField.setBorder(clearStyle);
        userField.setBorder(clearStyle);
        startDate.setBorder(clearStyle);
        startHour.setBorder(clearStyle);
        startMin.setBorder(clearStyle);
        endDate.setBorder(clearStyle);
        endHour.setBorder(clearStyle);
        endMinute.setBorder(clearStyle);
    }
}
