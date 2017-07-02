package com.example.krishnapandey.banaaosession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(ProfileActivity.this,NewSession.class));
                finish();
            }
        });
    }
}
