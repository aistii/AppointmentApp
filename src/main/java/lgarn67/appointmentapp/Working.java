package lgarn67.appointmentapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lgarn67.appointmentapp.Appointment;
import lgarn67.appointmentapp.Customer;

import java.io.IOException;

public class Working {
    static Stage stage;
    Parent scene;

    // ObservableList is what is used to populate table view.
    // I don't think it's actually good if you have lots of stuff to hold lol,
    // because observable list is in memory.

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private static int loggedInId;
    public static int getLoggedInId() {
        return loggedInId;
    }
    public static void setLoggedInId(int loggedInId) {
        Working.loggedInId = loggedInId;
    }

    //TODO
    // Create a method that allows the program to store the logged in person's id.

    /*  ===================================
    *   ===== CUSTOMER RELATED PARTS  =====
    *   - The addCustomer() method is mainly to populate the
    *     OL itself, rather than to add to the database.
    *   - getAllCustomers() will populate the table, once
    *     the OL is populated through addCustomer().
    *   - NOTE: The SQL queries will add to the actual database!
    */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
    public static void addCustomer (Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    /*  =======================================
     *  ====== LOCATION RELATED PARTS  ========
     *  - These lists will not be modified by the user.
     *  - They are used by the combo boxes and then ultimately
     *    the queries that use the country and division objects.
     *  - For the most part, these will populate the lists
     *    and return them when we look at the combo boxes.
     */

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static void addCountry (Country newCountry) {
        allCountries.add(newCountry);
    }

    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }

    public static void addDivision (Division newDivision) {
        allDivisions.add(newDivision);
    }

    public static void removeDivision(Division division) {
        allDivisions.remove(division);
    }

    public static void removeDivision(int index) {
        allDivisions.remove(index);
    }

    public static void removeCountry (Country country) {
        allCountries.remove(country);
    }

    public static void removeCountry (int index) {
        allCountries.remove(index);
    }


    public static void resetCountries() {
        int i;
        for (i = 0; i < allCountries.size(); i++) {
            removeCountry(i);
        }
    }

    public static void resetDivisions() {
        int i;
        for (i = 0; i < allDivisions.size(); i++) {
            removeDivision(i);
        }
    }
    /*  =======================================
     *  ====== APPOINTMENT RELATED PARTS  =====
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }




}
