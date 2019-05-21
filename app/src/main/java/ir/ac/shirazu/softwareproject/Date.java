package ir.ac.shirazu.softwareproject;

public class Date {
    private String dayOfWeek;
    private String date;


    public Date(String date) {
        this.date = date;
        this.dayOfWeek = setDayOfWeek();
    }


    private static String setDayOfWeek() {
        String[] day =
                new String[]{
                        "شنبه",
                        "یکشنبه",
                        "دوشنبه",
                        "سه شنبه",
                        "چهارشنبه",
                        "پنجشنبه",
                        "جمعه"};
        // ToDo: Add a persian calendar library to
        //  fill data of dayOfWeek property
        // Use third-party library here to calculate the day of week in integer
        int dayNum = 0; // شنبه
        return day[dayNum];
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
