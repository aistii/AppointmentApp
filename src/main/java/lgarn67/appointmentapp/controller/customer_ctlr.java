package lgarn67.appointmentapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lgarn67.appointmentapp.Customer;
import lgarn67.appointmentapp.Working;
import lgarn67.appointmentapp.dao.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class customer_ctlr implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustTable.setItems(Working.getAllCustomers());
        CustIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        CustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        CustPhoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        CustAddrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CustZipCol.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        CustCtryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        CustFLDCol.setCellValueFactory(new PropertyValueFactory<>("fld"));
    }
    Stage stage;
    Parent scene;
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



    @FXML public void clickCustBtn(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/customerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void clickSBEXit(ActionEvent e) {
        Platform.exit();
    }

    @FXML public void ClickAddCust(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/lgarn67/appointmentapp/addCust.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void ClickEditCust(ActionEvent e) throws IOException {

    }

    @FXML public void ClickDelCust(ActionEvent e) throws IOException {

    }


}
