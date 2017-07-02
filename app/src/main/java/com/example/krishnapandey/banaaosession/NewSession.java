package com.example.krishnapandey.banaaosession;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class NewSession extends AppCompatActivity {
    private TimePicker timePicker1;

    private TextView from_button;
    private TextView to_button;
    private Button ok_action;
    private Button cancel_action;
    private Button add_student_action;
    private EditText input_Session_name;
    private EditText input_location;
    private EditText input_topic;

    private boolean flag;
    private boolean time;


    private ListView list_view;

    ArrayList<String> list;
    private HashMap<String, String> studentList;

    private String time_from;
    private String time_to;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);

        //Initialise Buttons
        input_Session_name = (EditText) findViewById(R.id.input_Session_name);
        input_location = (EditText) findViewById(R.id.input_location);
        input_topic = (EditText) findViewById(R.id.input_topic);
        from_button = (TextView) findViewById(R.id.from_button);
        to_button = (TextView) findViewById(R.id.to_button);
        ListView lView = (ListView)findViewById(R.id.list_view);
        ok_action = (Button) findViewById(R.id.ok_action);
        add_student_action = (Button) findViewById(R.id.add_student_action);
        cancel_action = (Button) findViewById(R.id.cancel_action);

        list = new ArrayList<String>();
        studentList=new HashMap<String, String>();

//        OnClick Buttons
        add_student_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NewSession.this,Pop.class),5);
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
        from_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flag = true;
                //startActivityForResult(new Intent(NewSession.this,Pop.class),5);
                setTime(true);


            }

        });

        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        lView.setAdapter(adapter);


        ok_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetail();

            }
        });

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void getDetail() {
        if (studentList.size() == 0) {
            Toast.makeText(NewSession.this, "Add some students first", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = input_Session_name.getText().toString().trim();
        String location = input_location.getText().toString().trim();
        String topic = input_topic.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            input_Session_name.setError("Enter your full name");
            //Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            input_Session_name.requestFocus();
            return;
        }

        else if (TextUtils.isEmpty(location)) {
            input_location.setError("Enter the contact no");
            //Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            input_location.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(topic)) {
            input_topic.setError("Enter the usercode");
            //Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
            input_topic.requestFocus();
            return;
        } else if (time_from.isEmpty()) {
            Toast.makeText(this, "Set Time First", Toast.LENGTH_SHORT).show();
            return;
        }else if (time_to.isEmpty()) {
            Toast.makeText(this, "Set Time First", Toast.LENGTH_SHORT).show();
            return;
        }
        SessionInformation sessionInformation = new SessionInformation(name, location, topic, time_from, time_to, studentList);

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //FirebaseUser user = mAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).setValue(sessionInformation);
        databaseReference.child(name).setValue(sessionInformation);

        Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            String name = data.getStringExtra("NAME");
            list.add(name);
            studentList.put(name, name);
            /*MyCustomAdapter adapter = new MyCustomAdapter(list, this);
            ListView lView = (ListView)findViewById(R.id.list_view);
            lView.setAdapter(adapter);
*/

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
                    time_from = selectedHour +" "+ selectedMinute;
                    from_button.setText( "From : "+selectedHour + ":" + selectedMinute);
                } else {
                    time_to = selectedHour +" "+ selectedMinute;
                    to_button.setText( "To : "+selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
