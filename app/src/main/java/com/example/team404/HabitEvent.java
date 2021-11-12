package com.example.team404;

import java.util.ArrayList;

public class HabitEvent implements java.io.Serializable{
    private String location;
    private String comments;
    private String date;

    public HabitEvent(String location, String  comments, String date) {
        this.location = location;
        this.comments = comments;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
