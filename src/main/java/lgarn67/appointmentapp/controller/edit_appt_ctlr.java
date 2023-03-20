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
import lgarn67.appointmentapp.*;
import lgarn67.appointmentapp.conversion.TimeChecks;
import lgarn67.appointmentapp.dao.AppointmentQuery;
import lgarn67.appointmentapp.dao.ContactQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class edit_appt_ctlr implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // You should reset every time a page loads
            Working.resetContacts();
            Working.resetUsers();
            Working.resetCustomers();
            ContactQuery.selectAllContacts();
            UserQuery.selectAllUsers();
            CustomerQuery.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        timeZoneLabel.setText(String.valueOf(TimeChecks.getLocalZoneId()));

        //Converts will change the way its formatted.
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
        startHour.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return null;
            }

            @Override
            public Integer fromString(String s) {
                return null;
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

    Stage stage;
    Parent scene;
    @FXML private TextField idField;
    @FXML private Button CancelBtn;
    @FXML private Button SaveBtn;
    @FXML private Label contErrMsg;
    @FXML private ComboBox<Contact> contactField;
    @FXML private Label custErrMsg;
    @FXML private ComboBox<Customer> custField;
    @FXML private Label descErrMsg;
    @FXML private TextArea descField;
    @FXML private DatePicker endDate;
    @FXML private Label endErrMsg;
    @FXML private Spinner<Integer> endHour;
    @FXML private Spinner<Integer> endMinute;
    @FXML private Label locErrMsg;
    @FXML private TextField locField;
    @FXML private DatePicker startDate;
    @FXML private Label startErrMsg;
    @FXML private Spinner<Integer> startHour;
    @FXML private Spinner<Integer> startMin;
    @FXML private Label titleErrMsg;
    @FXML private TextField titleField;
    @FXML private Label typeErrMsg;
    @FXML private TextField typeField;
    @FXML private Label userErrMsg;
    @FXML private ComboBox<User> userField;
    @FXML private Label timeZoneLabel;
    Border errorStyle = new Border(new BorderStroke(Color.valueOf("#EF596F"), BorderStrokeStyle.SOLID, null, new BorderWidths(1)));
    Border clearStyle = new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.NONE, null, new BorderWidths(0)));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // loader variables
    // note: the variables regarding time are going to have to have the hour, minute, and dates split up into respective fields.
    static int loadedId;
    static String loadedTitle;
    static String loadedDesc;
    static String loadedLoc;
    static String loadedType;
    static LocalDateTime loadedStart;
    static LocalDateTime loadedEnd;
    static Customer loadedCust;
    static User loadedUser;
    static Contact loadedCont;

    //TODO
    // Create a method that can translate the timestamps from the database
    // into local date times. I'm thinking:
    // (Database) Timestamp >> (App) ZonedDateTime >> (App) LocalDateTime
    // UPDATE: This was done in appointment query.

    @FXML void clickSaveAppt (ActionEvent event) throws SQLException {
        if (checkCompletion()){
            if (checkLength()) {
                // some try catch for the other values
                try {
                    LocalDate apptStartDate = LocalDate.parse(startDate.getEditor().getText(), dateFormat);
                    LocalDate apptEndDate = LocalDate.parse(endDate.getEditor().getText(), dateFormat);
                    try {
                        int apptStartHr = Integer.parseInt(startHour.getEditor().getText());
                        int apptStartMin = Integer.parseInt(startMin.getEditor().getText());
                        int apptEndHr = Integer.parseInt(endHour.getEditor().getText());
                        int apptEndMin = Integer.parseInt(endMinute.getEditor().getText());

                        // Make the date time stuff.
                        LocalDateTime userStartLDT = LocalDateTime.of(apptStartDate, LocalTime.of(apptStartHr, apptStartMin));
                        ZonedDateTime userStartZDT = ZonedDateTime.of(userStartLDT, TimeChecks.getLocalZoneId());
                        ZonedDateTime testUserStartEastern = userStartZDT.withZoneSameInstant(TimeChecks.getEastZoneId());

                        LocalDateTime userEndLDT = LocalDateTime.of(apptEndDate, LocalTime.of(apptEndHr, apptEndMin));
                        ZonedDateTime userEndZDT = ZonedDateTime.of(userEndLDT, TimeChecks.getLocalZoneId());
                        ZonedDateTime testUserEndEastern = userEndZDT.withZoneSameInstant(TimeChecks.getEastZoneId());
                        //TODO
                        // All of these messages regarding the appointment time availabilities
                        // need to be a popup message because those are important.
                        if (TimeChecks.checkStartingTime(testUserStartEastern)) {
                            if (TimeChecks.checkEndingTime(testUserStartEastern, testUserEndEastern)) {
                                System.out.println("This appointment time is valid!");
                                int custId = custField.getSelectionModel().getSelectedItem().getId();
                                int apptId = Integer.parseInt(idField.getText());
                                // Get all of the fields
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
                                        e.printStackTrace();
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert_ctlr.loadDialogStyle(alert);
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Error Saving Appointment");
                                        alert.setContentText("Please ensure data is inputted correctly.");
                                        alert.showAndWait();
                                    }
                                }
                            } else {
                                System.out.println("This appointment end time is not within business hours!");
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert_ctlr.loadDialogStyle(alert);
                                alert.setTitle("Error");
                                alert.setHeaderText("Appointment Time Error");
                                alert.setContentText("Please ensure appointment end time is after the start time and before 10pm eastern time.");
                                alert.showAndWait();
                            }
                        } else {
                            //System.out.println("This appointment start time is not within business hours!");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert_ctlr.loadDialogStyle(alert);
                            alert.setTitle("Error");
                            alert.setHeaderText("Appointment Time Error");
                            alert.setContentText("Please ensure appointment start time is after 8am eastern time.");
                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        startErrMsg.setText("Please ensure the time values are numerical.");
                        endErrMsg.setText("Please ensure the time values are numerical.");
                    }
                } catch (Exception e) {
                    // trying to see what errors are thrown exactly
                    e.printStackTrace();
                    startErrMsg.setText("Please ensure date is correctly typed in.");
                }
            }
        }
    }

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
        loadedCont = selA.getContact(); // due to this using the contact object
    }

    @FXML void clickCancel(ActionEvent event) throws IOException {
        resetFields();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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
                //e.printStackTrace();
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
                //e.printStackTrace();
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
        // If you want to do the get text with something that's not
        // exactly a text field but has one (like datepicker/spinner) use:
        // endDate.getEditor().getText()
        // for the spinners we're going to get the value in the text field.
        startHour.getEditor().clear();
        startMin.getEditor().clear();
        endHour.getEditor().clear();
        endMinute.getEditor().clear();
        Working.resetCustomers();
        Working.resetUsers();
        Working.resetContacts();
        resetErrorMsg();
    }

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
