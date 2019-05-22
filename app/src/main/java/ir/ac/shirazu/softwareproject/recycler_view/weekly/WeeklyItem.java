package ir.ac.shirazu.softwareproject.recycler_view.weekly;


import ir.ac.shirazu.softwareproject.MealInfo;

public class WeeklyItem {
    private MealInfo breakfastInfo;
    private MealInfo lunchInfo;
    private MealInfo dinnerInfo;


    public WeeklyItem(MealInfo breakfastInfo,
                      MealInfo lunchInfo,
                      MealInfo dinnerInfo) {
        this.breakfastInfo = breakfastInfo;
        this.lunchInfo = lunchInfo;
        this.dinnerInfo = dinnerInfo;
    }

    public MealInfo getBreakfastInfo() {
        return breakfastInfo;
    }

    public void setBreakfastInfo(MealInfo breakfastInfo) {
        this.breakfastInfo = breakfastInfo;
    }

    public MealInfo getLunchInfo() {
        return lunchInfo;
    }

    public void setLunchInfo(MealInfo lunchInfo) {
        this.lunchInfo = lunchInfo;
    }


    public MealInfo getDinnerInfo() {
        return dinnerInfo;
    }

    public void setDinnerInfo(MealInfo dinnerInfo) {
        this.dinnerInfo = dinnerInfo;
    }

}
