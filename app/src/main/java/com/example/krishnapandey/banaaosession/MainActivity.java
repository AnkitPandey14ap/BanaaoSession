package com.example.krishnapandey.banaaosession;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Activities.CompletedSessionActivity;
import com.example.krishnapandey.banaaosession.Activities.LoginActivity;
import com.example.krishnapandey.banaaosession.Activities.MySessionActivityNew;
import com.example.krishnapandey.banaaosession.Activities.NewSessionBottomActivity;
import com.example.krishnapandey.banaaosession.Activities.ProfileActivity;
import com.example.krishnapandey.banaaosession.Activities.UpdateActivity;
import com.example.krishnapandey.banaaosession.Adapters.MySessionAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.MySessionData;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String T = "Ankit";
    private ListView listView;
    //    private BottomNavigationView navigation;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    ArrayList<MySessionData> sessionList = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewSessionBottomActivity.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);










        initialization();
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressDialog = new ProgressDialog(MainActivity.this);
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
                    sessionList.add(new MySessionData(sessionInformation.name, sessionInformation.timeFrom, sessionInformation.timeTo));
                }
                makeListView(sessionList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Ankit","cancelled");

            }
        });
        setUserName();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(T,"clicked "+sessionList.get(position).getSessionName());

                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("NAME", sessionList.get(position).getSessionName());
                startActivity(intent);

            }
        });







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_workshop) {

        } else if (id == R.id.nav_students) {

        }else if (id == R.id.nav_workshop_complete) {
            startActivity(new Intent(MainActivity.this,CompletedSessionActivity.class));


        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.log_out) {

            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeListView(ArrayList<MySessionData> list) {
        /*adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);*/

        MySessionAdapter mySessionAdapter = new MySessionAdapter(sessionList, MainActivity.this);
        listView.setAdapter(mySessionAdapter);
        progressDialog.hide();
    }



    private void initialization() {
//        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        list.add("Android");


    }


    public DatabaseReference getDatabaseReference() {

        return NewSessionBottomActivity.getDatabaseReference();
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        Toast.makeText(this, "Log Out successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    void setUserName() {
        SharedPreferences prefs = getSharedPreferences("com.example.krishnapandey.banaaosession",
                MODE_PRIVATE);
        String username = prefs.getString("UserName",
                "Username1");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.usernameTextView);
        navUsername.setText(username);
    }
}
