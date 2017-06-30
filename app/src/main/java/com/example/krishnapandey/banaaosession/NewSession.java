package com.example.krishnapandey.banaaosession;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class NewSession extends AppCompatActivity {
    private TimePicker timePicker1;
    private TextView from_button;
    private TextView to_button;
    private boolean flag;
    private boolean time;

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
                //flag = true;
                //startActivityForResult(new Intent(NewSession.this,Pop.class),5);
                setTime(true);


            }
        });
        to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flag = false;
                //startActivityForResult(new Intent(NewSession.this,Pop.class),5);
                setTime(false);
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

    public void setTime(final boolean time) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NewSession.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (time) {
                    from_button.setText( selectedHour + ":" + selectedMinute);
                } else {
                    to_button.setText( selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
