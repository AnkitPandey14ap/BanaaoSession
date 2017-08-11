package com.example.krishnapandey.banaaosession.DataClasses;

/**
 * Created by krishna on 11/8/17.
 */

public class StudentInfo {
    public String name;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
    StudentInfo(){

    }


}
