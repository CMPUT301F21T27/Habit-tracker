package com.example.team404.HabitEvent;

import java.util.ArrayList;

public class HabitEvent implements java.io.Serializable{
    //--------------------------------
    //Create habit event object
    //--------------------------------
    private String id;
    private String uri;
    private String location;
    private String comments;
    private String date;

    public HabitEvent(String id, String uri, String location, String comments, String date) {
        this.id = id;
        this.uri = uri;
        this.location = location;
        this.comments = comments;
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
