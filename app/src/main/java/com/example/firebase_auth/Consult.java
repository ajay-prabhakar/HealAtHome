package com.example.firebase_auth;

import com.google.firebase.database.Exclude;

/**
 * Created by Chromicle on 6/7/19.
 */
public class Consult {
    String symtonms;
    String time;
    String doctor;
    String Address;

    public Consult(){

    }

    public Consult(String symtonms, String time, String doctor, String address) {
        this.symtonms = symtonms;
        this.time = time;
        this.doctor = doctor;
        Address = address;
    }

    public String getSymtonms() {
        return symtonms;
    }

    public String getTime() {
        return time;
    }

    public String getDoctor() {
        return doctor;
    }


    @Exclude
    public String getAddress() {
        return Address;
    }
}
