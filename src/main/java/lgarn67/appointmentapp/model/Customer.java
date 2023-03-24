package lgarn67.appointmentapp.model;

/**
 * Model class for customers.
 * @author Leanna Garner
 */
public class Customer {

    /**
     * The Id.
     */
    private int id;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Address.
     */
    private String address;
    /**
     * The Phone num.
     */
    private String phoneNum;
    /**
     * The Country.
     */
    private Country country;
    /**
     * The first level division.
     */
    private Division fld;
    /**
     * The first level division id.
     */
    private int fldId;
    /**
     * The Post code.
     */
    private String postCode;

    /**
     * Instantiates a new Customer.
     *
     * @param id       the id
     * @param name     the name
     * @param address  the address
     * @param phoneNum the phone num
     * @param country  the country
     * @param fld      the fld
     * @param postCode the post code
     */
    public Customer(int id, String name, String address, String phoneNum, Country country, Division fld, String postCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.country = country;
        this.fld = fld;
        this.postCode = postCode;
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

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets phone num.
     *
     * @return the phone num
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets phone num.
     *
     * @param phoneNum the phone num
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Gets fld.
     *
     * @return the fld
     */
    public Division getFld() {
        return fld;
    }

    /**
     * Sets fld.
     *
     * @param fld the fld
     */
    public void setFld(Division fld) {
        this.fld = fld;
    }

    /**
     * Gets post code.
     *
     * @return the post code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets post code.
     *
     * @param postCode the post code
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Gets fld id.
     *
     * @return the fld id
     */
    public int getFldId() {
        return fldId;
    }

    /**
     * Sets fld id.
     *
     * @param fldId the fld id
     */
    public void setFldId(int fldId) {
        this.fldId = fldId;
    }
    @Override
    public String toString() {
        return (id + " | " + name);
    }
}
