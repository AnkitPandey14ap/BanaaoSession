package com.example.krishnapandey.banaaosession.DataClasses;

/**
 * Created by krishna pandey on 16-07-2017.
 */

public class MySessionData {
    String sessionName;
    String timeFrom;
    String timeTo;
    String date;

    public MySessionData(String sessionName, String timeFrom,String timeTo,String date) {
        this.sessionName = sessionName;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.date= date;
    }


    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }



    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getTimeFrom() {
        return timeFrom;
    }
    public String getDate() {
        return date;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
