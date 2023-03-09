package lgarn67.appointmentapp;

public class Division {
    private int divisionId;
    private String divName;

    public Division(int divisionId, String divName) {
        this.divisionId = divisionId;
        this.divName = divName;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivName() {
        return divName;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    @Override
    public String toString(){
        return divName;
    }
}
