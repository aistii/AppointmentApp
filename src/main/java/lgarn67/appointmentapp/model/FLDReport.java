package lgarn67.appointmentapp.model;

public class FLDReport {
    private String country;
    private String fld;
    private int count;

    public FLDReport(String country, String fld, int count) {
        this.country = country;
        this.fld = fld;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFld() {
        return fld;
    }

    public void setFld(String fld) {
        this.fld = fld;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
