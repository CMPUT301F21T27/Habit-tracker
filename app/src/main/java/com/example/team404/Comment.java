package com.example.team404;

import java.io.Serializable;

public class Comment implements Serializable {
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }


    public String getComment() {
        return this.comment;
    }
}
