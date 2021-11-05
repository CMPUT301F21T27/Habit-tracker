package com.example.team404;

import java.util.ArrayList;

public class HabitEvent implements java.io.Serializable{
    private String location;
    private ArrayList<Comment> comments;

    public HabitEvent(String location) {
        this.location = location;
        this.comments = comments;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
