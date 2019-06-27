package ir.ac.shirazu.softwareproject.server_api.Meal;

import saman.zamani.persiandate.PersianDate;

public class Date {
    private int day;
    private int month;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date(int day, int month, int year) {
        validate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date, boolean isForSpinner) {
        PersianDate p = new PersianDate();
        if (isForSpinner) {
            String[] a = date.split(" ");
            a = a[1].split("/");
            this.year = Integer.valueOf(a[0]);
            this.month = Integer.valueOf(a[1]);
            this.day = Integer.valueOf(a[2]);
        } else {
            String[] s = date.split("/");
            int year = Integer.valueOf(s[0]);
            int month = Integer.valueOf(s[1]);
            int day = Integer.valueOf(s[2]);
            int[] data = p.toJalali(year, month, day);
            this.year = data[0];
            this.month = data[1];
            this.day = data[2];
        }
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

//
//    private static String setDayOfWeek() {
//        String[] day =
//                new String[]{
//                        "شنبه",
//                        "یکشنبه",
//                        "دوشنبه",
//                        "سه شنبه",
//                        "چهارشنبه",
//                        "پنجشنبه",
//                        "جمعه"};
//        // ToDo: Add a persian calendar library to
//        //  fill data of dayOfWeek property
//        // Use third-party library here to calculate the day of year in integer
//        int dayNum = 0; // شنبه
//        return day[dayNum];
//    }

    public String getDayOfWeek() {
        PersianDate tmp = new PersianDate();
        tmp.setShYear(this.year);
        tmp.setShMonth(this.month);
        tmp.setShDay(this.day);
        return tmp.dayName();

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

    public boolean compare(String s) {

        String str[] = s.split(" ");

        if (str[1].equals(this.getDateInString())) return true;
        else return false;
    }

    public static Date today() {
        PersianDate a = new PersianDate();
        return new Date(a.getShDay(), a.getShMonth(), a.getShYear());
    }
}