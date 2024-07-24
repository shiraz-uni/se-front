package ir.ac.shirazu.softwareproject.server_api.Meal;

import java.util.List;

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
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date, boolean isForSpinner) {
        PersianDate p = new PersianDate();
        if (isForSpinner) {
            String[] a = date.split(" ");
            a = a[0].split("/");
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

    public Date(java.util.Date date) {
        convertDateToJalali(date);
    }

    private void convertDateToJalali(java.util.Date date) {

    }

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

        // String str[] = s.split(" ");

        if (s.equals(this.toString())) return true;
        else return false;
    }

    public boolean compare(Date date) {
        if (this.getDay() == date.getDay() && this.getMonth() == date.getMonth() && this.getYear() == date.getYear())
            return true;
        else return false;
    }

    public static Date getToday() {
        PersianDate date = new PersianDate();
        return new Date(date.getShDay(), date.getShMonth(), date.getShYear());
    }

    public void setToNextDay() {
        PersianDate date = new PersianDate();
        date.setShDay(this.day);
        date.setShMonth(this.month);
        date.setShYear(this.year);
        date.addDate(0, 0, 1);
        this.day = date.getShDay();
    }

    public Date increaseDayBy(int i) {
        PersianDate date = new PersianDate();
        date.setShDay(this.day);
        date.setShMonth(this.month);
        date.setShYear(this.year);
        date.addDate(0, 0, i);

        return new Date(date.getShDay(), date.getShMonth(), date.getShYear());
    }

    public static Date getFirstDayOfThisWeek() {
        Date firstDayOfWeek = getToday();
        PersianDate date = new PersianDate();

        date.setShDay(firstDayOfWeek.getDay());
        date.setShMonth(firstDayOfWeek.getMonth());
        date.setShYear(firstDayOfWeek.getYear());
        date.addDate(0, 0, -date.dayOfWeek() + 1);
        firstDayOfWeek.setDay(date.getShDay());
        return firstDayOfWeek;
    }

    @Override
    public String toString() {

        return this.getDateInString() + " " + this.getDayOfWeek();
    }

    public boolean equals(Object obj) {
        Date date = (Date) obj;
        if (date.day == this.day && date.month == this.month
                && date.year == this.year)
            return true;
        return false;
    }

    public List<String> sortByDayOfWeek(List<String> a) {

        return null;
    }
}