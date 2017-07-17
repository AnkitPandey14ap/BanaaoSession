package com.example.krishnapandey.banaaosession.DataClasses;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishna pandey on 01-07-2017.
 */

public class SessionInformation {
    public String name;
    public String location;
    public String timeFrom;
    public String timeTo;
    public boolean completed;

    public HashMap<String, String> studentsName = new HashMap<String, String>();
//    public HashMap<String, String> topic = new HashMap<String, String>();
    public HashMap<String, UserData> topic = new HashMap<>();
    public HashMap<String, String> trainer = new HashMap<String, String>();

    public SessionInformation(String name, String location,String timeFrom, String timeTo,
                              HashMap<String, String> studentsName, HashMap<String, UserData> topic, HashMap<String, String> trainer) {
        this.name = name;
        this.location = location;
        //this.topic = topic;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;


        this.studentsName = studentsName;


        this.topic = topic;


        this.trainer = trainer;

    }

    public SessionInformation(boolean completed) {
        this.completed = completed;
    }

    SessionInformation(){

    }

}
