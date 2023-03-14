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
    private static ObservableList<Appointment> monthAppt = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weekAppt = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    private static int loggedInId;
    public static int getLoggedInId() {
        return loggedInId;
    }
    public static void setLoggedInId(int loggedInId) {
        Working.loggedInId = loggedInId;
    }
    private static String loggedInName;

    public static String getLoggedInName() {
        return loggedInName;
    }

    public static void setLoggedInName(String loggedInName) {
        Working.loggedInName = loggedInName;
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

    public static void resetCustomers() {
        allCustomers.clear();
    }
    public static void addCustomer (Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    public static void removeCustomer (Customer customer) {
        allCustomers.remove(customer);
    }

    public static void removeCustomer (int index) {
        allCustomers.remove(index);
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
        allCountries.clear();
    }

    public static void resetDivisions() {
        allDivisions.clear();
    }
    /*  =======================================
     *  ====== APPOINTMENT RELATED PARTS  =====
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static ObservableList<Appointment> getMonthAppt() {
        return monthAppt;
    }

    public static ObservableList<Appointment> getWeekAppt() {
        return weekAppt;
    }

    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }

    public static void removeAppointment (Appointment appointment) {
        allAppointments.remove(appointment);
    }

    public static void removeAppointment (int index) {
        allAppointments.remove(index);
    }

    public static void resetAllAppointment () {
       allAppointments.clear();
    }

    public static void addApptMonth(Appointment newAppointment) {
        monthAppt.add(newAppointment);
    }

    public static void removeMonthAppointment(Appointment appointment) {
        monthAppt.remove(appointment);
    }

    public static void removeMonthAppointment (int index) {
        monthAppt.remove(index);
    }

    public static void resetMonthAppointment () {
        monthAppt.clear();
    }

    public static void addApptWeek(Appointment newAppointment) {
        weekAppt.add(newAppointment);
    }

    public static void removeWeekAppointment(Appointment appointment) {
        weekAppt.remove(appointment);
    }

    public static void removeWeekAppointment (int index) {
        weekAppt.remove(index);
    }

    public static void resetWeekAppointment () {
        weekAppt.clear();
    }


    /*  =======================================
     *  ====== USER AND CONTACT METHODS  ======
     */

    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }
    public static void addContact (Contact newContact) {
        allContacts.add(newContact);
    }
    public static void removeContact (Contact contact) {
        allContacts.remove(contact);
    }
    public static void removeContact (int index) {
        allContacts.remove(index);
    }
    public static void resetContacts() {
        allContacts.clear();
    }
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }
    public static void addUser (User newUser) {
        allUsers.add(newUser);
    }
    public static void removeUser (User user) {
        allUsers.remove(user);
    }
    public static void removeUser (int index) {
        allUsers.remove(index);
    }
    public static void resetUsers (){
        allUsers.clear();
    }
}
