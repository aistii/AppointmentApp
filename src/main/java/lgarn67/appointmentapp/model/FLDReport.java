package lgarn67.appointmentapp.model;

/**
 * Model class for holding items in the country-FLD-count report.
 * @author Leanna Garner
 */
public class FLDReport {
    /**
     * The Country.
     */
    private String country;
    /**
     * The Fld.
     */
    private String fld;
    /**
     * The Count.
     */
    private int count;

    /**
     * Instantiates a new Fld report entry.
     *
     * @param country the country
     * @param fld     the fld
     * @param count   the count
     */
    public FLDReport(String country, String fld, int count) {
        this.country = country;
        this.fld = fld;
        this.count = count;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets fld.
     *
     * @return the fld
     */
    public String getFld() {
        return fld;
    }

    /**
     * Sets fld.
     *
     * @param fld the fld
     */
    public void setFld(String fld) {
        this.fld = fld;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
