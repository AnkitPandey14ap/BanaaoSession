package com.example.krishnapandey.banaaosession.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Activities.NewSessionBottomActivity;
import com.example.krishnapandey.banaaosession.Activities.ProfileActivity;
import com.example.krishnapandey.banaaosession.Adapters.MyPopUpAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicPopUp extends AppCompatActivity {

    private BottomNavigationView navigation;
    private ListView completedListView;
    private ListView incompletedListView;

    MyPopUpAdapter myPopUpAdapter;
    MyPopUpAdapter myPopUpAdapter1;
    private String sessionName;

    ArrayList<UserData> list = new ArrayList<>();
    ArrayList<UserData> list1 = new ArrayList<>();

    HashMap<String,UserData> listToSave = new HashMap<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    /*startActivity(new Intent(TopicPopUp.this, ProfileActivity.class));
                    finish();*/
                    return true;
                case R.id.navigation_dashboard:
                    saveList();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };

    private void saveList() {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        Log.i("Ankit","clicked "+listToSave);
        NewSessionBottomActivity.getDatabaseReference().child(Nodes.session).child(sessionName).child(Nodes.topic).setValue(listToSave);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_pop_up);
        sessionName = getIntent().getStringExtra("NAME");
        initialize();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        makeCompletedListView();

    }

    private void makeCompletedListView() {

        NewSessionBottomActivity.getDatabaseReference().child(Nodes.session).child(sessionName).child(Nodes.topic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                list1.clear();

                Log.i("Ankit","datasnapshot");
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.i("Ankit","datasnapshot 1");

                for (DataSnapshot child : children ) {
                    Log.i("Ankit","datasnapshot 2");

                    UserData ud = child.getValue(UserData.class);
                    if (ud.isSelected()) {
                        list.add(new UserData(ud.isSelected(),ud.getUserName()));
                        listToSave.put(ud.getUserName(), ud);
                    } else {
                        list1.add(new UserData(ud.isSelected(),ud.getUserName()));
                        listToSave.put(ud.getUserName(), ud);

                    }
//                    list.add(new UserData(ud.isSelected(),ud.getUserName()));
                    Log.i("Ankit","data "+list.size());

                }
                myPopUpAdapter = new MyPopUpAdapter(TopicPopUp.this, list);
                completedListView.setAdapter(myPopUpAdapter);

                myPopUpAdapter1 = new MyPopUpAdapter(TopicPopUp.this, list1);
                incompletedListView.setAdapter(myPopUpAdapter1);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        myPopUpAdapter = new MyPopUpAdapter(this, list);
        completedListView.setAdapter(myPopUpAdapter);
        completedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData userData = list.get(position);
                if (userData.isSelected()) {
                    userData.setSelected(false);
                    listToSave.put(userData.getUserName(), userData);

                } else {
                    userData.setSelected(true);
                    listToSave.put(userData.getUserName(), userData);

                }
                Toast.makeText(TopicPopUp.this, "Total ", Toast.LENGTH_SHORT).show();
                //textView.setText("add "+count);
                myPopUpAdapter.notifyDataSetChanged();
            }
        });


        myPopUpAdapter1 = new MyPopUpAdapter(this, list1);
        incompletedListView.setAdapter(myPopUpAdapter1);
        incompletedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData userData = list1.get(position);
                if (userData.isSelected()) {
                    listToSave.put(userData.getUserName(), userData);
                    userData.setSelected(false);
                } else {
                    listToSave.put(userData.getUserName(), userData);
                    userData.setSelected(true);
                }
                Toast.makeText(TopicPopUp.this, "Total ", Toast.LENGTH_SHORT).show();
                myPopUpAdapter1.notifyDataSetChanged();
            }
        });

    }

    private void initialize() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        incompletedListView = (ListView) findViewById(R.id.incompletedListView);
        completedListView = (ListView) findViewById(R.id.completedListView);
    }
}
