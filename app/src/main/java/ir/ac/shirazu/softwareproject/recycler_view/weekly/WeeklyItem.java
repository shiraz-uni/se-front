package ir.ac.shirazu.softwareproject.recycler_view.weekly;

import ir.ac.shirazu.softwareproject.MeaInformation;

public class WeeklyItem {
    private String day;
    private String date;
    private MeaInformation brekfastInformation;
    private MeaInformation lunchInformation;
    private MeaInformation dinnerInformation;


    public WeeklyItem(String day,
                      String date,
                      MeaInformation brekfastInformation,
                      MeaInformation lunchInformation,
                      MeaInformation dinnerInformation) {
        this.day = day;
        this.date = date;
        this.brekfastInformation = brekfastInformation;
        this.lunchInformation = lunchInformation;
        this.dinnerInformation = dinnerInformation;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MeaInformation getBrekfastInformation() {
        return brekfastInformation;
    }

    public void setBrekfastInformation(MeaInformation brekfastInformation) {
        this.brekfastInformation = brekfastInformation;
    }

    public MeaInformation getLunchInformation() {
        return lunchInformation;
    }

    public void setLunchInformation(MeaInformation lunchInformation) {
        this.lunchInformation = lunchInformation;
    }

    public MeaInformation getDinnerInformation() {
        return dinnerInformation;
    }

    public void setDinnerInformation(MeaInformation dinnerInformation) {
        this.dinnerInformation = dinnerInformation;
    }

}
