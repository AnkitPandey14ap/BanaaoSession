package com.example.krishnapandey.banaaosession.Activities;

import android.content.Context;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by krishna pandey on 06-07-2017.
 */

public class EListView {
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public EListView(Context context) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

    }

    void updateAdapter() {

    }

    void getData() {

    }
}
