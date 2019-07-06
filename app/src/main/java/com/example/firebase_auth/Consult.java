package com.example.firebase_auth;

import com.google.firebase.database.Exclude;

/**
 * Created by Chromicle on 6/7/19.
 */
public class Consult {
    String Count;
    String time;
    String Additional;
    String Address;
    String diseses;

    public String getId() {
        return id;
    }

    String email;
    String id;

    public Consult() {

    }

    public Consult(String count, String time, String additional, String address, String diseses, String email, String id) {
        Count = count;
        this.time = time;
        Additional = additional;
        Address = address;
        this.diseses = diseses;
        this.email = email;
        this.id = id;
    }


    @Exclude
    public String getAdditional() {
        return Additional;
    }

    public String getEmail() {
        return email;
    }

    public String getDiseses() {
        return diseses;
    }

    @Exclude
    public String getCount() {
        return Count;
    }

    public String getTime() {
        return time;
    }

    public String getDoctor() {
        return Additional;
    }


    @Exclude
    public String getAddress() {
        return Address;
    }

}
