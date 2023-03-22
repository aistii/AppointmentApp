module lgarn67.appointmentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens lgarn67.appointmentapp to javafx.fxml;
    exports lgarn67.appointmentapp;
    exports lgarn67.appointmentapp.controller;
    opens lgarn67.appointmentapp.controller to javafx.fxml;
    exports lgarn67.appointmentapp.model;
    opens lgarn67.appointmentapp.model to javafx.fxml;
}