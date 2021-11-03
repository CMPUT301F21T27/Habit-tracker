package com.example.team404;

import java.io.Serializable;

public class Comment implements Serializable {
    private String account_photo;
    private String comment;

    public Comment(String account_photo, String comment) {
        this.account_photo = account_photo;
        this.comment = comment;
    }

    public String getAccount_photo() {
        return this.account_photo;
    }

    public String getComment() {
        return this.comment;
    }
}
