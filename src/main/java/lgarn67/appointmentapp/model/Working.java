package lgarn67.appointmentapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <p>Class for holding runtime variables: observable lists for the tables and logged in id/username for recordkeeping.</p>
 * <p>The Working class mainly performs on the observable lists populated by the SQL queries in the DAO package.
 * These observable lists are what populate the table, after the lists themselves are populated from the ResultSets from the classes in the DAO package.</p>
 * @author Leanna Garner
 */
public class Working {

    /**
     * The list of all customers from the database.
     */
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /**
     * The list of all appointments from the database.
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /**
     * The list of all countries from the database.
     */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /**
     * The list of first level divisions from the database.
     */
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    /**
     * The list of this month's appointments from the database.
     */
    private static ObservableList<Appointment> monthAppt = FXCollections.observableArrayList();
    /**
     * The list of this week's appointments from the database.
     */
    private static ObservableList<Appointment> weekAppt = FXCollections.observableArrayList();
    /**
     * The list of all contacts from the database.
     */
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    /**
     * The list of all users from the database.
     */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    /**
     * The list of entries in the country-FLD-count report.
     */
    private static ObservableList<FLDReport> fldReport = FXCollections.observableArrayList();
    /**
     * The list of entries in the month-type-count report.
     */
    private static ObservableList<MonthTypeReport> mtReport = FXCollections.observableArrayList();


    /**
     * The constant loggedInId.
     */
    private static int loggedInId;

    /**
     * Gets logged in id.
     *
     * @return the logged in id
     */
    public static int getLoggedInId() {
        return loggedInId;
    }

    /**
     * Sets logged in id.
     *
     * @param loggedInId the logged in id
     */
    public static void setLoggedInId(int loggedInId) {
        Working.loggedInId = loggedInId;
    }

    /**
     * The constant loggedInName.
     */
    private static String loggedInName;

    /**
     * Gets logged in username.
     *
     * @return the logged in name
     */
    public static String getLoggedInName() {
        return loggedInName;
    }

    /**
     * Sets logged in username.
     *
     * @param loggedInName the logged in username
     */
    public static void setLoggedInName(String loggedInName) {
        Working.loggedInName = loggedInName;
    }

    /**
     * Gets all customers from the list.
     *
     * @return the all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * Reset all customer list.
     */
    public static void resetCustomers() {
        allCustomers.clear();
    }

    /**
     * Add customer to the observable list.
     *
     * @param newCustomer the new customer
     */
    public static void addCustomer (Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    /**
     * Gets all countries from the list.
     *
     * @return the all countries
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * Add country to the observable list.
     *
     * @param newCountry the new country
     */
    public static void addCountry (Country newCountry) {
        allCountries.add(newCountry);
    }

    /**
     * Gets all first level divisions from the list.
     *
     * @return the all divisions
     */
    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }

    /**
     * Add division to the observable list.
     *
     * @param newDivision the new division
     */
    public static void addDivision (Division newDivision) {
        allDivisions.add(newDivision);
    }

    /**
     * Reset the list of countries.
     */
    public static void resetCountries() {
        allCountries.clear();
    }

    /**
     * Reset the list of first level divisions.
     */
    public static void resetDivisions() {
        allDivisions.clear();
    }

    /**
     * Gets all appointments from the list.
     *
     * @return the all appointments
     */

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Gets all appointments in the current month's list.
     *
     * @return the month appt
     */
    public static ObservableList<Appointment> getMonthAppt() {
        return monthAppt;
    }

    /**
     * Gets all appointment in the current week's list.
     *
     * @return the week appt
     */
    public static ObservableList<Appointment> getWeekAppt() {
        return weekAppt;
    }

    /**
     * Adds appointment to the list.
     *
     * @param newAppointment the new appointment
     */
    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }

    /**
     * Resets the list of all appointments.
     */
    public static void resetAllAppointment () {
       allAppointments.clear();
    }

    /**
     * Add appointment to the current month list.
     *
     * @param newAppointment the new appointment
     */
    public static void addApptMonth(Appointment newAppointment) {
        monthAppt.add(newAppointment);
    }

    /**
     * Resets the current month's list of appointments.
     */
    public static void resetMonthAppointment () {
        monthAppt.clear();
    }

    /**
     * Add appointment to the current week list.
     *
     * @param newAppointment the new appointment
     */
    public static void addApptWeek(Appointment newAppointment) {
        weekAppt.add(newAppointment);
    }

    /**
     * Resets the current week's list of appointments
     */
    public static void resetWeekAppointment () {
        weekAppt.clear();
    }

    /**
     * Gets the list of rows of the country-FLD-count report.
     *
     * @return the fld report
     */
    public static ObservableList<FLDReport> getFldReport() {
        return fldReport;
    }

    /**
     * Adds entry to the country-FLD-count report.
     *
     * @param row the row
     */
    public static void addToFLDRep(FLDReport row) {
        fldReport.add(row);
    }

    /**
     * Resets the list of entries for the country-FLD-count report.
     */
    public static void resetFLDRep() {
        fldReport.clear();
    }

    /**
     * Gets the list of entries for the month-type-count report.
     *
     * @return the mt report
     */
    public static ObservableList<MonthTypeReport> getMtReport() {
        return mtReport;
    }

    /**
     * Adds entry to the month-type-count report.
     *
     * @param row the row
     */
    public static void addToMTRep(MonthTypeReport row) {
        mtReport.add(row);
    }

    /**
     * Resets the list of entries for the month-type-count report.
     */
    public static void resetMTRep() {
        mtReport.clear();
    }

    /**
     * Gets all contacts.
     *
     * @return the all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * Add contact to list.
     *
     * @param newContact the new contact
     */
    public static void addContact (Contact newContact) {
        allContacts.add(newContact);
    }

    /**
     * Reset contacts list.
     */
    public static void resetContacts() {
        allContacts.clear();
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Add user to list.
     *
     * @param newUser the new user
     */
    public static void addUser (User newUser) {
        allUsers.add(newUser);
    }

    /**
     * Reset users list.
     */
    public static void resetUsers (){
        allUsers.clear();
    }
}
