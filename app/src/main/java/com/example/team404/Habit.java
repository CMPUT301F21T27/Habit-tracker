package com.example.team404;

import java.util.Date;

public class Habit implements java.io.Serializable {
    private String title;
    private String reason;
    private String year;
    private String month;
    private String day;

    Habit(String t, String r, String year, String month, String day){
        setTitle(t);
        setReason(r);
        setYear(year);
        setMonth(month);
        setDay(day);

    }

    /**
     * Sets habit title with a character limit of 20
     * @param t
     * Habit title
     */
    public void setTitle(String t) {
        if(t.length() > 20 ){
            this.title = t.substring(0,20);
        }
        else{
            this.title = t;
        }
    }

    /**
     * Sets habit reason with a character limit of 20
     * @param r
     * Habit reason
     */
    public void setReason(String r) {
        if(r.length() > 30 ){
            this.reason = r.substring(0,30);
        }
        else{
            this.reason = r;
        }
    }

    /**
     * Sets habit date
     * @param year
     * Habit start year
     */
    public void setYear(String year){this.year = year;}
    /**
     * Sets habit date
     * @param month
     * Habit start month
     */
    public void setMonth(String month){this.month = month;}
    /**
     * Sets habit date
     * @param day
     * Habit start year
     */
    public void setDay(String day){this.day = day;}


    /**
     * Returns the title of the habit
     * @return title
     */
    public String getTitle(){return title;}

    /**
     * Returns the reason of the habit
     * @return reason
     */
    public String getReason(){return reason;}

    /**
     * Returns the start Date of the habit
     * @return Year
     */
    public String getYear(){return year;}
    /**
     * Returns the start Date of the habit
     * @return Month
     */
    public String getMonth(){return month;}
    /**
     * Returns the start Date of the habit
     * @return Day
     */
    public String getDay(){return day;}
}
