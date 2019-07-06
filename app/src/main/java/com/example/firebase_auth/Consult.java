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
    String email;

    public Consult(){

    }

    public Consult(String symtonms, String time, String doctor, String address, String additional, String email) {
        this.Count = symtonms;
        this.time = time;
        this.Additional = doctor;
        Address = address;
        this.diseses = additional;
        this.email = email;
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
