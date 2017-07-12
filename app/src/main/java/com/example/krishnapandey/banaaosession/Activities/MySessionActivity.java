package com.example.krishnapandey.banaaosession.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySessionActivity extends AppCompatActivity {
    private ListView list_view;
    private ArrayList list;
    private String TAG="Ankit";

    ProgressDialog progressDialog;


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_session);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data....");
        progressDialog.show();
        list_view = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<ListData>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("session");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children ) {
                    SessionInformation sessionInformation = child.getValue(SessionInformation.class);
                    //ListData sessionInformation = new ListData(sessionInformation.name,sessionInformation.timeFrom,sessionInformation.timeTo, sessionInformation.completed);
                    list.add(sessionInformation);

                    listDataHeader.add(sessionInformation.name);
                    List<String> top250 = new ArrayList<String>();
                    top250.add(sessionInformation.name);
                    top250.add("From: "+sessionInformation.timeFrom+"  To: "+sessionInformation.timeTo);
                    top250.add("Students :- ");


                    HashMap<String, String> names = sessionInformation.names;
                    names.keySet();

                    for(String key : names.keySet()) {
                        Log.i(TAG,"The key is: " + key + ",value is :" + names.get(key));
                        top250.add("    * "+names.get(key));
                    }
                    listDataChild.put(sessionInformation.name, top250);
                }
                updateListAdapter();
                progressDialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                progressDialog.hide();
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        updateListAdapter();
    }

    void updateListAdapter() {
        listAdapter = new com.example.krishnapandey.banaaosession.Adapters.ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MySessionActivity.this,ProfileActivity.class));
        finish();
    }

    public static class ListData{
        public String name;
        public String from;
        public String to;

        public ListData(String name, String from, String to, boolean completed) {

            this.name = name;
            this.from = from;
            this.to = to;
        }
    }

}
