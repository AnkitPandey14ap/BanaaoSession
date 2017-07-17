package com.example.krishnapandey.banaaosession.DataClasses;

import android.os.Parcelable;

/**
 * Created by krishna pandey on 16-07-2017.
 */
public class UserData {
    private boolean isSelected;
    private String userName;

    UserData() {
        
    }
    public UserData(boolean isSelected, String userName) {

        this.isSelected = isSelected;
        this.userName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getUserName() {
        return userName;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


}
