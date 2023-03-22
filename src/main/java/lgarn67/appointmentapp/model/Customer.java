package lgarn67.appointmentapp.model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String phoneNum;
    private Country country;
    private Division fld;
    private int fldId;
    private String postCode;

    public Customer(int id, String name, String address, String phoneNum, Country country, Division fld, String postCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.country = country;
        this.fld = fld;
        this.postCode = postCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Division getFld() {
        return fld;
    }

    public void setFld(Division fld) {
        this.fld = fld;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getFldId() {
        return fldId;
    }

    public void setFldId(int fldId) {
        this.fldId = fldId;
    }
    @Override // specifically for the comboboxes since the other places use getters.
    public String toString() {
        return (Integer.toString(id)+ " | " + name);
    }
}
