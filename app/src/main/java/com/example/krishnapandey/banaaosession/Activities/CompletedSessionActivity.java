package com.example.krishnapandey.banaaosession.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Adapters.MySessionAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.MySessionData;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompletedSessionActivity extends AppCompatActivity {

    private static final String T = "Ankit";
    private ListView listView;
    private BottomNavigationView navigation;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    ArrayList<MySessionData> sessionList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    finish();
                    //sessionNameEditText.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //sessionNameEditText.setText(R.string.title_dashboard);
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_session_new);

        initialization();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressDialog = new ProgressDialog(CompletedSessionActivity.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        //fetch the data from the server and show in the listView
        getDatabaseReference().child(Nodes.session).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sessionList.clear();
                Log.i("Ankit","fetching data");
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children ) {
                    SessionInformation sessionInformation = child.getValue(SessionInformation.class);
                    if (sessionInformation.completed) {
                        sessionList.add(new MySessionData(sessionInformation.name, sessionInformation.timeFrom, sessionInformation.timeTo));
                    }
                }
                makeListView(sessionList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Ankit","cancelled");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(T,"clicked "+sessionList.get(position).getSessionName());

                Intent intent=new Intent(CompletedSessionActivity.this,UpdateActivity.class);
                intent.putExtra("NAME", sessionList.get(position).getSessionName());
                startActivity(intent);

            }
        });


    }

    private void makeListView(ArrayList<MySessionData> list) {
        /*adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);*/

        MySessionAdapter mySessionAdapter = new MySessionAdapter(sessionList, CompletedSessionActivity.this);
        listView.setAdapter(mySessionAdapter);
        progressDialog.hide();
    }

    private void initialization() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        list.add("Android");


    }


    public DatabaseReference getDatabaseReference() {

        return NewSessionBottomActivity.getDatabaseReference();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();
        if (res_id == R.id.action_log_out) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(CompletedSessionActivity.this,LoginActivity.class));
        Toast.makeText(this, "Log Out successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

}

