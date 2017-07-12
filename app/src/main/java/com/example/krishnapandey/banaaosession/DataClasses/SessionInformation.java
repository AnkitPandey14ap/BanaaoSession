package com.example.krishnapandey.banaaosession.DataClasses;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishna pandey on 01-07-2017.
 */

public class SessionInformation {
    public String name;
    public String location;
    public String topic;
    public String timeFrom;
    public String timeTo;
    public boolean completed;

    public HashMap<String, String> names;

    public SessionInformation(String name, String location, String topic, String timeFrom, String timeTo, HashMap<String, String> students) {
        this.name = name;
        this.location = location;
        this.topic = topic;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        names = new HashMap<String, String>();
        names = students;

    }

    public SessionInformation(boolean completed) {
        this.completed = completed;
    }

    SessionInformation(){

    }

}
