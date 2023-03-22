package lgarn67.appointmentapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lgarn67.appointmentapp.model.Customer;
import lgarn67.appointmentapp.model.Working;
import lgarn67.appointmentapp.dao.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class customer_ctlr implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Working.resetCustomers();
            CustomerQuery.selectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CustTable.setItems(Working.getAllCustomers());
        CustIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        CustPhoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        CustAddrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustZipCol.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        CustCtryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustFLDCol.setCellValueFactory(new PropertyValueFactory<>("fld"));

        CustTable.getSelectionModel().clearSelection();

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

        CustAddBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addCust.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.setScene(new Scene(scene));
            stage.show();
        });
        SBExitBtn.setOnAction(e -> Platform.exit());
    }
    Stage stage;
    Parent scene;
    @FXML private Button SBCustomerBtn;
    @FXML private Button SBApptBtn;
    @FXML private Button SBExitBtn;
    @FXML private Button SBReportBtn;
    @FXML private Button CustAddBtn;
    @FXML private Button CustEditBtn;
    @FXML private Button CustRemBtn;
    @FXML private TableView<Customer> CustTable;
    @FXML private TableColumn<Customer, String> CustAddrCol;
    @FXML private TableColumn<Customer, String> CustCtryCol;
    @FXML private TableColumn<Customer, String> CustFLDCol;
    @FXML private TableColumn<Customer, Integer> CustIdCol;
    @FXML private TableColumn<Customer, String> CustNameCol;
    @FXML private TableColumn<Customer, String> CustPhoCol;
    @FXML private TableColumn<Customer, String> CustZipCol;
    private DialogPane dia;



    /*@FXML public void clickCustBtn(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void clickSBEXit(ActionEvent e) {
        Platform.exit();
    }

    @FXML public void clickAddCust(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addCust.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void clickApptBtn(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/appointmentsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }*/

    @FXML public void clickEditCust(ActionEvent e) throws IOException {
        //TODO
        // Call the method in edit customer controller once that's prepared.
        try {
            Customer selC = CustTable.getSelectionModel().getSelectedItem();
            edit_cust_ctlr.loadData(CustomerQuery.selectCustomer(selC.getId()));
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/editCust.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert_ctlr.loadDialogStyle(alert);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to edit.");
            alert.showAndWait();
            ex.printStackTrace();
        }
    }

    /*@FXML
    void goToReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/reportsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }*/

    @FXML public void clickDelCust(ActionEvent e) throws IOException, SQLException {
        // 2023.03.16 currently being used as a way to show dialog window.
        //Alert alert = new Alert(Alert.AlertType.ERROR, "Hello text");
        //alert.setHeaderText("Testing the header here");
        /*Alert alert = new Alert(Alert.AlertType.ERROR, "Hello!");
        alert_ctlr.loadDialogStyle(alert);
        alert.showAndWait();*/
        try {
            Customer selC = CustTable.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            alert_ctlr.loadDialogStyle(confirm);
            confirm.setTitle("Confirm Customer Deletion");
            confirm.setHeaderText("Confirm Customer Deletion");
            confirm.setContentText("Deleting the customer "+selC.getName()+ " will delete this record " +
                    "and any associated appointments. Continue?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                CustomerQuery.deleteCustomer(selC.getId());
                try {
                    Working.resetCustomers();
                    CustomerQuery.selectAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Alert executed = new Alert(Alert.AlertType.INFORMATION, "Customer record of " +selC.getName()+
                        " and associated appointments were deleted.");
                alert_ctlr.loadDialogStyle(executed);
                executed.showAndWait();
            }
            CustTable.getSelectionModel().clearSelection();

        } catch (Exception ex) {
            //ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert_ctlr.loadDialogStyle(alert);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
    }


}
