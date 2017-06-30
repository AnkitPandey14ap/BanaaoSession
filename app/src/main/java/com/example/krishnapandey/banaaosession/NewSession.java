package com.example.krishnapandey.banaaosession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class NewSession extends AppCompatActivity {
    private TimePicker timePicker1;
    private TextView from_button;
    private TextView to_button;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);
/*
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        Log.i("Ankit", hour + " " + min);*/

        from_button = (TextView) findViewById(R.id.from_button);
        to_button = (TextView) findViewById(R.id.to_button);

        from_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                startActivityForResult(new Intent(NewSession.this,Pop.class),5);

            }
        });
        to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                startActivityForResult(new Intent(NewSession.this,Pop.class),5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            String hour = data.getStringExtra("HOUR");
            String min = data.getStringExtra("MINUTE");

            if(flag){
                from_button.setText("From : "+hour+":"+min);
                flag = false;
                return;
            }else {
                to_button.setText("To : "+hour+":"+min);
                return;
            }

        }
    }
}
