package com.example.firebase_auth;

/**
 * Created by Chromicle on 6/7/19.
 */
public class Profile {
    String spec;
    String num;
    String age;
    String email;

    public Profile(String spec, String num, String age, String email) {
        this.spec = spec;
        this.num = num;
        this.age = age;
        this.email = email;
    }

    public String getSpec() {
        return spec;
    }

    public String getNum() {
        return num;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
