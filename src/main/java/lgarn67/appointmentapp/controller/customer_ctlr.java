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
import lgarn67.appointmentapp.dao.CustomerQuery;
import lgarn67.appointmentapp.model.Customer;
import lgarn67.appointmentapp.model.Working;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller for the main customer screen.
 * This has a table of all customers, and includes add, edit, and delete appointment buttons.
 */
public class customer_ctlr implements Initializable {
    /**
     * <p>Initializes the scene and populates the table.</p>
     * <p>The lambdas here are to create the onAction for the sidebar and add buttons to change scenes, without the need to have a separate named method:</p>
     * <ul>
     *     <li>to switch scenes between appointment, customer and report view, with the sidebar buttons;</li>
     *     <li>to go to the add customer screen when the button is clicked.</li>
     * </ul>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Working.resetCustomers();
            CustomerQuery.selectAll();
        } catch (SQLException throwables) {
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
        // Lambda for setting the add customer button to go to that scene.
        CustAddBtn.setOnAction(e -> {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addCust.fxml"));
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
     * The sidebar's button to switch to "customer" view.
     */
    @FXML private Button SBCustomerBtn;
    /**
     * The sidebar's button to switch to "appointment" view.
     */
    @FXML private Button SBApptBtn;
    /**
     * The sidebar's button to close the app.
     */
    @FXML private Button SBExitBtn;
    /**
     * The sidebar's button to switch to "report" view.
     */
    @FXML private Button SBReportBtn;
    /**
     * The add customer button.
     */
    @FXML private Button CustAddBtn;
    /**
     * The TableView showing all customers from the database.
     */
    @FXML private TableView<Customer> CustTable;
    /**
     * Column in CustTable for customer address.
     */
    @FXML private TableColumn<Customer, String> CustAddrCol;
    /**
     * Column in CustTable for customer country.
     */
    @FXML private TableColumn<Customer, String> CustCtryCol;
    /**
     * Column in CustTable for customer first-level division.
     */
    @FXML private TableColumn<Customer, String> CustFLDCol;
    /**
     * Column in CustTable for customer ID.
     */
    @FXML private TableColumn<Customer, Integer> CustIdCol;
    /**
     * Column in CustTable for customer name.
     */
    @FXML private TableColumn<Customer, String> CustNameCol;
    /**
     * Column in CustTable for customer phone number.
     */
    @FXML private TableColumn<Customer, String> CustPhoCol;
    /**
     * Column in CustTable for customer zip/postal code.
     */
    @FXML private TableColumn<Customer, String> CustZipCol;


    /**
     * Gets the ID of the selected customer, if any is selected. THe ID is useed to query the database which is then sent to the edit customer controller.
     * If no appointment is selected prior to pressing "edit", it will have an error message notifying the user.
     */
    @FXML public void clickEditCust(ActionEvent e) throws IOException {
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
        }
    }

    /**
     * Deletes the selected customer, if there is a customer that is selected.
     * It will pull the ID of the selected customer.
     * Once deleted, it will clear the Observable Lists in the Working class and then repopulate them.
     * An error message will show up if no customer is selected before pushing delete.
     */
    @FXML public void clickDelCust(ActionEvent e) throws IOException, SQLException {
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
                Working.resetCustomers();
                CustomerQuery.selectAll();
                Alert executed = new Alert(Alert.AlertType.INFORMATION, "Customer record of " +selC.getName()+
                        " and associated appointments were deleted.");
                alert_ctlr.loadDialogStyle(executed);
                executed.showAndWait();
            }
            CustTable.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert_ctlr.loadDialogStyle(alert);
            alert.setTitle("No Customer Selected");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
    }


}
