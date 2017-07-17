package com.example.krishnapandey.banaaosession.DataClasses;

/**
 * Created by krishna pandey on 29-06-2017.
 */

public class UserInformation {
    public String name;
    public String phone;
    public String usercode;
    public String email;

    public UserInformation(String name, String phone, String usercode, String email) {
        this.name = name;
        this.phone = phone;
        this.usercode = usercode;
        this.email= email;

    }
    UserInformation() {
        
    }
}
