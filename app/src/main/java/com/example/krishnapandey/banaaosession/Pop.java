package com.example.krishnapandey.banaaosession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by krishna pandey on 30-06-2017.
 */

public class Pop extends Activity {
    private EditText input_name;
    private EditText input_usercode;
    private EditText input_email;
    private EditText input_phone_no;

    private Button cancel_action;
    private Button ok_action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_member);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height= displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        input_name = (EditText) findViewById(R.id.input_name);
        input_usercode= (EditText) findViewById(R.id.input_usercode);
        input_email = (EditText) findViewById(R.id.input_email);
        input_phone_no = (EditText) findViewById(R.id.input_phone_no);

        cancel_action = (Button) findViewById(R.id.cancel_action);
        ok_action= (Button) findViewById(R.id.ok_action);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input_name.getText().toString().trim();
                String phone = input_phone_no.getText().toString().trim();
                String usercode = input_usercode.getText().toString().trim();
                String email = input_email.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    input_name.setError("Enter your full name");
                    Toast.makeText(Pop.this, "enter email", Toast.LENGTH_SHORT).show();
                    input_name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    input_phone_no.setError("Enter the contact no");
                    Toast.makeText(Pop.this, "enter password", Toast.LENGTH_SHORT).show();
                    input_phone_no.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(usercode)) {
                    input_usercode.setError("Enter the usercode");
                    Toast.makeText(Pop.this, "enter email", Toast.LENGTH_SHORT).show();
                    input_usercode.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    input_email.setError("Enter the email");
                    Toast.makeText(Pop.this, "enter email", Toast.LENGTH_SHORT).show();
                    input_email.requestFocus();
                    return;
                }

                Intent intent=new Intent();
                intent.putExtra("NAME", name);
                setResult(2,intent);
                finish();


            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
