package ir.ac.shirazu.softwareproject;

public class Date {
    private int day;
    private int month;
    private int year;
    private String dayOfWeek;

    public Date(int day, int month, int year) {
        validate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfWeek = setDayOfWeek();
    }

    private void validate(int day, int month, int year) {
        if ((day > 31 || day < 0) || (month > 12 || month < 0))
            new Exception("Date is out of range!", new Throwable());
    }

    public Date(java.util.Date date) {
        convertDateToJalali(date);
    }

    private void convertDateToJalali(java.util.Date date) {

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
        // Use third-party library here to calculate the day of year in integer
        int dayNum = 0; // شنبه
        return day[dayNum];
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDateInString() {
        String day = String.valueOf(this.day);
        String month = String.valueOf(this.month);
        if (day.length() == 1)
            day = fixedString(day);
        if (month.length() == 1)
            month = fixedString(month);
        return year + "/" + month + "/" + day;
    }

    private String fixedString(String num) {
        return "0" + num;
    }

    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setDate(java.util.Date date) {
        convertDateToJalali(date);
    }
}
