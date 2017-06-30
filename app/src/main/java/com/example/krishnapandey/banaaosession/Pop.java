package com.example.krishnapandey.banaaosession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by krishna pandey on 30-06-2017.
 */

public class Pop extends Activity {
    private TextView ok_btn;
    private TextView cancel_btn;
    private TimePicker timePicker1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.time_picker);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height= displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok_btn = (TextView) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTime();
            }
        });

    }

    private void sendTime() {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();

        Intent intent=new Intent();
        intent.putExtra("HOUR",String.valueOf(hour));
        intent.putExtra("MINUTE",String.valueOf(min));

        setResult(2,intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
