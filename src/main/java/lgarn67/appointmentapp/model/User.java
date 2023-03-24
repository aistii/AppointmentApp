package lgarn67.appointmentapp.model;

/**
 * <p>Model class for users. This is used in the combo box for the appointment edit/creation screens.</p>
 * @author Leanna Garner
 */
public class User {
    /**
     * The Id.
     */
    private int id;
    /**
     * The Username.
     */
    private String username;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     */
    public User(int id, String username) {
        this.id = id;
        this.username = username;
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
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return (id + " | " + username);
    }
}
