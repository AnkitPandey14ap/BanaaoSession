package com.example.krishnapandey.banaaosession.PopUps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Activities.NewSessionBottomActivity;
import com.example.krishnapandey.banaaosession.Adapters.MyPopUpAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AttendancePopUp extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView listView;
    private MyPopUpAdapter myPopUpAdapter;
    ArrayList<UserData> list = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    private Button save_action;
    private Button cancel_action;
    private String sessionName;

    private void saveData() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Log.i("Ankit","date "+dateFormat.format(date));

        HashMap<String, UserData> map = new HashMap<String, UserData>();
        for (UserData ud: list) {
            map.put(ud.getUserName(), ud);
        }

//        getRef().child(Nodes.attendance).child(dateFormat.format(date)).setValue(map);
        getRef().child(Nodes.attendance).child(dateFormat.format(date)).setValue(map, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference ref) {
                if (databaseError != null) {
                    Toast.makeText(AttendancePopUp.this, "Data could not be saved " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    Toast.makeText(AttendancePopUp.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                    System.out.println("Data saved successfully.");
                }

            }
        });




        /*getRef().child(Nodes.attendance).child(dateFormat.format(date)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(AttendancePopUp.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AttendancePopUp.this, "failed to Save \nCheck your internet connection", Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_pop_up);
        sessionName = getIntent().getStringExtra("NAME");
        Log.i("Ankit", "name " + sessionName);
        initialisze();
        getRef().child("studentsName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                for (String key: map.keySet()) {
                    System.out.println("key : " + key);
                    list.add(new UserData(false,map.get(key)));
                }
                myPopUpAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        save_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                finish();
            }
        });
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData userData = list.get(position);
                if (userData.isSelected()) {
                    userData.setSelected(false);
                    arrayList.remove(userData.getUserName());
                    //count = count - 1;
                } else {
                    userData.setSelected(true);
                    arrayList.add(userData.getUserName());
                    //count = count + 1;
                }
//                Toast.makeText(AttendancePopUp.this, "Total " + count, Toast.LENGTH_SHORT).show();
                //textView.setText("add " + count);
                myPopUpAdapter.notifyDataSetChanged();
            }
        });






    }

    private void initialisze() {
        mTextMessage = (TextView) findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.listView);
        myPopUpAdapter = new MyPopUpAdapter(this, list);
        listView.setAdapter(myPopUpAdapter);

        save_action = (Button) findViewById(R.id.save_action);
        cancel_action = (Button) findViewById(R.id.cancel_action);


    }

    public DatabaseReference getRef() {
        Log.i("Ankit","sesion name "+sessionName);

        DatabaseReference ref= NewSessionBottomActivity.getDatabaseReference().child(Nodes.session)
                .child(sessionName);
        return ref;
    }
}
