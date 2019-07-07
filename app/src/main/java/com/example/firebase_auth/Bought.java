package com.example.firebase_auth;

import com.google.firebase.database.Exclude;

/**
 * Created by Chromicle on 7/7/19.
 */
public class Bought {

    String Additional;
    String userMail;
    String desieses;
    String count;
    String location;

    public Bought(){

    }

    public Bought(String additional, String userMail, String desieses, String count, String location) {
        Additional = additional;
        this.userMail = userMail;
        this.desieses = desieses;
        this.count = count;
        this.location = location;
    }

    @Exclude
    public String getAdditional() {
        return Additional;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getDesieses() {
        return desieses;
    }

    public String getCount() {
        return count;
    }

    public String getLocation() {
        return location;
    }
}
