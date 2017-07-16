package com.example.krishnapandey.banaaosession.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private Button button_new_session;
    private Button button_my_session;
    private Button button_admin_report;
    private Button button_completed_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_new_session = (Button) findViewById(R.id.button_new_session);
        button_my_session = (Button) findViewById(R.id.button_my_session);
        button_admin_report = (Button) findViewById(R.id.button_admin_report);
        button_completed_session = (Button) findViewById(R.id.button_completed_session);

        button_new_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,NewSessionBottomActivity.class));
            }
        });
        button_my_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MySessionActivityNew.class));
                //finish();
            }
        });
        button_completed_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,CompletedSessionActivity.class));
                finish();
            }
        });

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
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
        finish();
    }
}
