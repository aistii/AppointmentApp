package lgarn67.appointmentapp.model;

/**
 * Model class for first level divisions. ID is used to query the database. Name is used to populate the TableView and ComboBoxes in the UI.
 * @author Leanna Garner
 */
public class Division {
    /**
     * The Division id.
     */
    private int divisionId;
    /**
     * The Div name.
     */
    private String divName;

    /**
     * Instantiates a new Division.
     *
     * @param divisionId the division id
     * @param divName    the div name
     */
    public Division(int divisionId, String divName) {
        this.divisionId = divisionId;
        this.divName = divName;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets div name.
     *
     * @return the div name
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Sets div name.
     *
     * @param divName the div name
     */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    @Override
    public String toString(){
        return divName;
    }
}
