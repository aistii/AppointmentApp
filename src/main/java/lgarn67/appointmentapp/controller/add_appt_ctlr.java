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
import lgarn67.appointmentapp.Contact;
import lgarn67.appointmentapp.Customer;
import lgarn67.appointmentapp.User;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.conversion.TimezoneConversion;
import lgarn67.appointmentapp.dao.ContactQuery;
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.dao.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class add_appt_ctlr implements Initializable {

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

        timeZoneLabel.setText(String.valueOf(TimezoneConversion.getLocalZoneId()));

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
        startHour.getEditor().setText(null);
        startMin.getEditor().setText(null);
        endHour.getEditor().setText(null);
        endMinute.getEditor().setText(null);

        // For combobox format, we will use the assumption in the
        custField.setItems(Working.getAllCustomers());
        contactField.setItems(Working.getAllContacts());
        userField.setItems(Working.getAllUsers());

    }


    Stage stage;
    Parent scene;
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



    @FXML void clickCancel(ActionEvent event) throws IOException {
        resetFields();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML void clickSaveAppt(ActionEvent event) {
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
                        ZonedDateTime userStartZDT = ZonedDateTime.of(userStartLDT, TimezoneConversion.getLocalZoneId());
                        ZonedDateTime testUserStartEastern = userStartZDT.withZoneSameInstant(TimezoneConversion.getEastZoneId());

                        System.out.println(userStartZDT);
                        System.out.println(testUserStartEastern);
                        LocalTime testingTime = LocalTime.of(testUserStartEastern.getHour(), testUserStartEastern.getMinute());
                        System.out.println(testingTime);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        startErrMsg.setText(startErrMsg.getText() + " Please ensure the time values are numerical.");
                        endErrMsg.setText(endErrMsg.getText() + " Please ensure the time values are numerical.");
                    }
                } catch (Exception e) {
                    // trying to see what errors are thrown exactly
                    e.printStackTrace();
                    startErrMsg.setText("Please ensure date is correctly typed in.");
                }
            }
        }
    }
    public boolean checkLength () {
        int titleLength = titleField.getLength();
        int locLength = locField.getLength();
        int descLength = descField.getLength();
        int typeLength = typeField.getLength();

        if ((titleLength > 50) || (locLength > 50) || (descLength > 50) || (typeLength > 50)) {
            if (titleLength > 50) {
                titleField.setBorder(errorStyle);
                Integer overCt = titleLength;
                titleErrMsg.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            if (locLength > 50) {
                locField.setBorder(errorStyle);
                Integer overCt = locLength;
                locErrMsg.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            if (descLength > 50) {
                descField.setBorder(errorStyle);
                Integer overCt = descLength;
                descErrMsg.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            if (typeLength > 50) {
                typeField.setBorder(errorStyle);
                Integer overCt = typeLength;
                typeErrMsg.setText("Maximum 50 characters; currently has " + overCt.toString() + " characters.");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCompletion () {
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

        if (titleField.getText().isBlank() || locField.getText().isBlank() || descField.getText().isBlank() ||
            typeField.getText().isBlank() || contactField.getSelectionModel().isEmpty() || custField.getSelectionModel().isEmpty() ||
            userField.getSelectionModel().isEmpty() || startDate.getEditor().getText().isEmpty() || endDate.getEditor().getText().isEmpty() ||
            startHour.getEditor().getText().isEmpty() || startMin.getEditor().getText().isEmpty() || endHour.getEditor().getText().isEmpty() ||
            endMinute.getEditor().getText().isEmpty()) {

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
            if (contactField.getSelectionModel().isEmpty()) {
                contactField.setBorder(errorStyle);
                contErrMsg.setText("Please select a contact.");
            }
            if (custField.getSelectionModel().isEmpty()) {
                custField.setBorder(errorStyle);
                custErrMsg.setText("Please select a customer.");
            }
            if (userField.getSelectionModel().isEmpty()) {
                userField.setBorder(errorStyle);
                userErrMsg.setText("Please select a user.");
            }
            try {
                if (startDate.getEditor().getText().isEmpty() || startHour.getEditor().getText().isEmpty() || startMin.getEditor().getText().isEmpty()) {
                    startDate.setBorder(errorStyle);
                    startHour.setBorder(errorStyle);
                    startMin.setBorder(errorStyle);
                    startErrMsg.setText("Please enter a start date and time.");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                startDate.setBorder(errorStyle);
                startHour.setBorder(errorStyle);
                startMin.setBorder(errorStyle);
                startErrMsg.setText("Please enter a start date and time.");
            }
            try {
                if (endDate.getEditor().getText().isEmpty()|| endHour.getEditor().getText().isEmpty() || endMinute.getEditor().getText().isEmpty()) {
                    endDate.setBorder(errorStyle);
                    endHour.setBorder(errorStyle);
                    endMinute.setBorder(errorStyle);
                    endErrMsg.setText("Please enter an end date and time.");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
