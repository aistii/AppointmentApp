package lgarn67.appointmentapp.model;

import java.time.LocalDateTime;

/**
 * Model class for appointments.
 * @author Leanna Garner
 */
public class Appointment {

    /**
     * The Id.
     */
    private int id;
    /**
     * The Title.
     */
    private String title;
    /**
     * The Descrip.
     */
    private String descrip;
    /**
     * The Location.
     */
    private String location;
    /**
     * The Contact.
     */
    private Contact contact;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Start date time.
     */
    private LocalDateTime startDateTime;
    /**
     * The End date time.
     */
    private LocalDateTime endDateTime;
    /**
     * The Customer.
     */
    private Customer customer;
    /**
     * The User.
     */
    private User user;

    /**
     * Instantiates a new Appointment.
     *
     * @param id            the id
     * @param title         the title
     * @param descrip       the descrip
     * @param location      the location
     * @param contact       the contact
     * @param type          the type
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     * @param customer      the customer
     * @param user          the user
     */
    public Appointment(int id, String title, String descrip, String location, Contact contact, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, Customer customer, User user) {
        this.id = id;
        this.title = title;
        this.descrip = descrip;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.user = user;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets descrip.
     *
     * @return the descrip
     */
    public String getDescrip() {
        return descrip;
    }

    /**
     * Sets descrip.
     *
     * @param descrip the descrip
     */
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start date time.
     *
     * @return the start date time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets start date time.
     *
     * @param startDateTime the start date time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets end date time.
     *
     * @return the end date time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets end date time.
     *
     * @param endDateTime the end date time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
