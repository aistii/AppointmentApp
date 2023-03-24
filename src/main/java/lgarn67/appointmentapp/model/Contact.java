package lgarn67.appointmentapp.model;

/**
 * Model class for contacts. Used in the appointment creation/edit screens, as well as the reports screen to select a contact's schedule.
 * @author Leanna Garner
 */

public class Contact {
    /**
     * The Id.
     */
    private int id;
    /**
     * The Name.
     */
    private String name;

    /**
     * Instantiates a new Contact.
     *
     * @param id   the id
     * @param name the name
     */
    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return (id + " | " + name);
    }
}
