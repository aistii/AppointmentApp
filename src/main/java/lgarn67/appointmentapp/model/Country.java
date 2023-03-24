package lgarn67.appointmentapp.model;

/**
 * Model class for countries. ID is used for queries. Name is used for reports and in populating the Country combobox in the add/edit customer screens.
 * @author Leanna Garner
 */
public class Country {

    /**
     * The Id.
     */
    private int id;
    /**
     * The Name.
     */
    private String name;

    /**
     * Instantiates a new Country.
     *
     * @param id   the id
     * @param name the name
     */
    public Country(int id, String name) {
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
        return name;
    }

}
