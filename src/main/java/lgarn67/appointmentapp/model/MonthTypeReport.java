package lgarn67.appointmentapp.model;

/**
 * Model class for holding items in the month-type-count report.
 * @author Leanna Garner
 */
public class MonthTypeReport {
    /**
     * The Month.
     */
    private String month;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Count.
     */
    private int count;

    /**
     * Instantiates a new Month type report.
     *
     * @param month the month
     * @param type  the type
     * @param count the count
     */
    public MonthTypeReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month = month;
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
