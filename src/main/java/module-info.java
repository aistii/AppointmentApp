module lgarn67.appointmentapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens lgarn67.appointmentapp to javafx.fxml;
    exports lgarn67.appointmentapp;
}