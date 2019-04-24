package ir.ac.shirazu.softwareproject.recycler_view.weekly;

import ir.ac.shirazu.softwareproject.FoodInformation;

public class WeeklyItem {
    private String day;
    private String date;
    private FoodInformation brekfastInformation;
    private FoodInformation lunchInformation;
    private FoodInformation dinnerInformation;


    public WeeklyItem(String day,
                      String date,
                      FoodInformation brekfastInformation,
                      FoodInformation lunchInformation,
                      FoodInformation dinnerInformation) {
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

    public FoodInformation getBrekfastInformation() {
        return brekfastInformation;
    }

    public void setBrekfastInformation(FoodInformation brekfastInformation) {
        this.brekfastInformation = brekfastInformation;
    }

    public FoodInformation getLunchInformation() {
        return lunchInformation;
    }

    public void setLunchInformation(FoodInformation lunchInformation) {
        this.lunchInformation = lunchInformation;
    }

    public FoodInformation getDinnerInformation() {
        return dinnerInformation;
    }

    public void setDinnerInformation(FoodInformation dinnerInformation) {
        this.dinnerInformation = dinnerInformation;
    }

}
