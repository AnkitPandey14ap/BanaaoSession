package com.example.krishnapandey.banaaosession.Activities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.PopUps.ListSelectionPopUp;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NewSessionBottomActivity extends AppCompatActivity {

    private static final String TAG = "Ankit";
    private RelativeLayout from_button;
    private RelativeLayout to_button;
    private TextView input_to;
    private TextView input_from;
    private EditText input_Session_name;
    private EditText input_location;
    private Button add_student_action;
    private Button add_topic_action;
    private Button add_trainer_action;

    Spinner spinnerStudents;

    ArrayList<String> list;
    ArrayList<String> studentNameList=new ArrayList<>();

    private HashMap<String, String> studentList;
    private HashMap<String, String> topicList;
    private HashMap<String, String> trainerList;

    HashMap<String, UserData> topicHashMap;

    private ListView lView;
    private String time_from;
    private String time_to;
    private ProgressDialog progressDialog;
    private static DatabaseReference databaseReference;
    private static FirebaseAuth mAuth;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    getDetail();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session_bottom);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intialize();
        getDatabaseReference();




        to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(false);
            }
        });
        from_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(true);


            }

        });
        add_student_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(NewSessionBottomActivity.this, ListSelectionPopUp.class);
                intent.putExtra("caller", "student");
                startActivityForResult(intent,0);
//                  startActivity(new Intent(NewSessionBottomActivity.this, ListSelectionPopUp.class));
            }
        });
        add_topic_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSessionBottomActivity.this, ListSelectionPopUp.class);
                intent.putExtra("caller", "topic");
                startActivityForResult(intent,1);}
        });
        add_trainer_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSessionBottomActivity.this, ListSelectionPopUp.class);
                intent.putExtra("caller", "trainer");
                startActivityForResult(intent,2);}
        });

/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewSessionBottomActivity.this,
                android.R.layout.simple_spinner_item,studentNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(adapter);
*/

        List<String> extraList = new ArrayList<>();
        extraList = studentNameList;
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewSessionBottomActivity.this,
                android.R.layout.simple_spinner_item, extraList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(adapter);
        spinnerStudents.setPrompt("Students");
        spinnerStudents.setOnItemSelectedListener(this);
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            studentNameList=data.getStringArrayListExtra("NAME LIST");
//      Copy the data of arrayList to List

//            storing the data of the List studentnameList to hasmap studentList
            studentList = new HashMap<>();
            for (int i = 0; i < studentNameList.size(); i++) {
                studentList.put(studentNameList.get(i), studentNameList.get(i));
            }
            List<String> extraList = new ArrayList<>();
            extraList = studentNameList;
//      Make spiner and give the data set to display
            Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, extraList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);




        }else if (resultCode == 1) {
//            ArrayList<UserData> userDatas = (ArrayList<UserData>) data.getSerializableExtra("LIST");
//            Log.i("Ankit", "userData "+String.valueOf(userDatas));
            studentNameList=data.getStringArrayListExtra("NAME LIST");
//      Copy the data of arrayList to List
            topicHashMap = new HashMap<String, UserData>();


            for (int i = 0; i < studentNameList.size(); i++) {
                topicHashMap.put(studentNameList.get(i), new UserData(false, studentNameList.get(i)));
            }


            topicList= new HashMap<>();
            for (int i = 0; i < studentNameList.size(); i++) {
                topicList.put(studentNameList.get(i), studentNameList.get(i));
            }
            List<String> extraList = new ArrayList<>();
            extraList = studentNameList;
//      Make spiner and give the data set to display
            Spinner dropdown = (Spinner)findViewById(R.id.spinnerTopic);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, extraList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);




        }else if (resultCode == 2) {
            studentNameList=data.getStringArrayListExtra("NAME LIST");
//      Copy the data of arrayList to List
            trainerList= new HashMap<>();
            for (int i = 0; i < studentNameList.size(); i++) {
                trainerList.put(studentNameList.get(i), studentNameList.get(i));
            }
            List<String> extraList = new ArrayList<>();
            extraList = studentNameList;
//      Make spiner and give the data set to display
            Spinner dropdown = (Spinner)findViewById(R.id.spinnerHandover);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, extraList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);
        }
    }

    private void intialize() {
        input_Session_name = (EditText) findViewById(R.id.input_Session_name);
        input_location = (EditText) findViewById(R.id.input_location);
        from_button = (RelativeLayout) findViewById(R.id.from_button);
        to_button = (RelativeLayout) findViewById(R.id.to_button);
        input_to = (TextView) findViewById(R.id.input_to);
        input_from = (TextView) findViewById(R.id.input_from);

        add_student_action = (Button) findViewById(R.id.add_student_action);
        add_topic_action = (Button) findViewById(R.id.add_topic_action);
        add_trainer_action = (Button) findViewById(R.id.add_trainer_action);

        spinnerStudents = (Spinner) findViewById(R.id.spinner1);

    }
    public void setTime(final boolean time) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NewSessionBottomActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (time) {
                    time_from = selectedHour +" "+ selectedMinute;
                    input_from.setText( selectedHour + ":" + selectedMinute);
                } else {
                    time_to = selectedHour +" "+ selectedMinute;
                    input_to.setText( selectedHour + ":" + selectedMinute);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }


    private void getDetail() {
        if (studentList.size() == 0) {
            Toast.makeText(NewSessionBottomActivity.this, "Add some students first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (topicList.size() == 0) {
            Toast.makeText(NewSessionBottomActivity.this, "Add some topic first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (trainerList.size() == 0) {
            Toast.makeText(NewSessionBottomActivity.this, "Add a trainer first", Toast.LENGTH_SHORT).show();
            return;
        }

        final String name = input_Session_name.getText().toString().trim();
        final String location = input_location.getText().toString().trim();


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
        } else if (time_from.equals("00:00")) {
            Toast.makeText(this, "Set Time First", Toast.LENGTH_SHORT).show();
            return;
        }else if (time_to.equals("00:00")) {
            Toast.makeText(this, "Set Time First", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(NewSessionBottomActivity.this);
        progressDialog.setMessage("saving data...");
        progressDialog.show();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SessionInformation sessionInformation = new SessionInformation(name,location,time_from,time_to,studentList,topicHashMap,trainerList);
                Log.i("Ankit",studentList.size()+" "+topicList.size()+" "+trainerList.size());
                //getDatabaseReference().child("dfdsf").child("hello").setValue("data");
                getDatabaseReference().child(Nodes.session).child(sessionInformation.name).setValue(sessionInformation);
                progressDialog.hide();

                Toast.makeText(NewSessionBottomActivity.this, "succesfull", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "String is " + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(NewSessionBottomActivity.this, "updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NewSessionBottomActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                Log.i("Ankit","canceled");

            }
        });

    }


    public static DatabaseReference getDatabaseReference() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference.child("extra").setValue("detail");
        return databaseReference;
    }
}
