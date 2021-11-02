package com.example.team404;

import java.util.Date;

public class Habit {
    private String title;
    private String reason;
    private Date startDate;

    Habit(String t, String r, Date d){
        setTitle(t);
        setReason(r);
        setStartDate(d);
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
     * @param d
     * Habit start date
     */
    public void setStartDate(Date d){this.startDate = d;}

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
     * @return date
     */
    public Date getStartDate(){return startDate;}
}
