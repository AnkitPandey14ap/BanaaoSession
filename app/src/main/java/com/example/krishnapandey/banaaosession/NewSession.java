package com.example.krishnapandey.banaaosession;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;


public class NewSession extends AppCompatActivity {
    private TimePicker timePicker1;
    private TextView from_button;
    private TextView to_button;
    private boolean flag;
    private boolean time;

    private ListView list_view;

    ArrayList<String> list;
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
/*
        String[] myStringArray = new String[4];
        myStringArray[0] = "ankit";
        myStringArray[1] = "pandey";
        myStringArray[2] = "mayank";
        myStringArray[3] = "shah";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringArray);

        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(adapter);*/

        list = new ArrayList<String>();
        list.add("item1");
        list.add("item2");

        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.list_view);
        lView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.list_view);
        lView.setAdapter(adapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            String name = data.getStringExtra("NAME");
            list.add(name);
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
