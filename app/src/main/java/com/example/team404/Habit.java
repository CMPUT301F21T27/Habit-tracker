package com.example.team404;

import java.util.Date;

public class Habit implements java.io.Serializable {
    private String id;
    private String title;
    private String reason;

    private String year;
    private String month;
    private String day;


    private Boolean monday ;
    private Boolean tuesday ;
    private Boolean wednesday ;
    private Boolean thursday ;
    private Boolean friday ;
    private Boolean saturday;
    private Boolean sunday;

    private Boolean pub;






    Habit(String id, String t, String r,String year, String month, String day) {
        setId(id);
        setTitle(t);
        setReason(r);


        setYear(year);
        setMonth(month);
        setDay(day);
        monday = false;
        tuesday = false;
        wednesday = false;
        thursday = false;
        friday = false;
        saturday = false;
        sunday = false;
        pub = false;


    }
    /**
     * Set is habit occur on Monday
     * @param m
     *
     */
    public void setMonday(Boolean m) {
        this.monday=m;

    }

    /**
     * Set is habit occur on Tuesday
     * @param t
     *
     */
    public void setTuesday(Boolean t) {
        this.tuesday=t;
    }
    /**
     * Set is habit occur on Wednesday
     * @param w
     *
     */
    public void setWednesday(Boolean w) {
        this.wednesday=w;
    }
    /**
     * Set is habit occur on Thursday
     * @param t
     *
     */
    public void setThursday(Boolean t) {
        this.thursday=t;
    }
    /**
     * Set is habit occur on Friday
     * @param f
     *
     */
    public void setFriday(Boolean f) {
        this.friday=f;
    }
    /**
     * Set is habit occur on Saturday
     * @param s
     *
     */
    public void setSaturday(Boolean s) {
        this.saturday=s;
    }
    /**
     * Set is habit occur on Sunday
     * @param s
     *
     */
    public void setSunday(Boolean s) {
        this.sunday=s;
    }

    /**
     * Sets habit title with a character limit of 20
     * @param i
     * Habit title
     */
    public void setId(String i) {
        this.id=i;
    }
    public void setPub(boolean p){
        this.pub=p;
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
     * Sets habit reason with a character limit of 30
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
    public String getId(){return id;}
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


    public Boolean getPub(){return pub;}

    public String getDay(){return day;}
    /**
     * Returns is the habit occur on Monday
     * @return monday
     */
    public Boolean getMonday() {return monday;}
    /**
     * Returns is the habit occur on Tuesday
     * @return tuesday
     */
    public Boolean getTuesday() {return tuesday;}
    /**
     * Returns is the habit occur on Wednesday
     * @return wednesday
     */
    public Boolean getWednesday() {return wednesday;}
    /**
     * Returns is the habit occur on Thursday
     * @return thursday
     */
    public Boolean getThursday() {return thursday;}
    /**
     * Returns is the habit occur on Friday
     * @return friday
     */
    public Boolean getFriday() {return friday;}
    /**
     * Returns is the habit occur on Saturday
     * @return saturday
     */
    public Boolean getSaturday() {return saturday;}
    /**
     * Returns is the habit occur on Sunday
     * @return sunday
     */
    public Boolean getSunday() {return sunday;}



}
