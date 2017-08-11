package com.example.krishnapandey.banaaosession;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.krishnapandey.banaaosession.Activities.MySessionActivityNew;
import com.example.krishnapandey.banaaosession.Activities.NewSessionBottomActivity;
import com.example.krishnapandey.banaaosession.Adapters.MySessionAdapter;
import com.example.krishnapandey.banaaosession.Adapters.StudentAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.MySessionData;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.DataClasses.StudentInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    private static final String T = "Ankit";
    private ListView listView;
    private ProgressDialog progressDialog;
    private ArrayList<StudentInfo> studentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        initialization();
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressDialog = new ProgressDialog(StudentListActivity.this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        getDatabaseReference().child(Nodes.allstudents).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                Log.i("Ankit","fetching data");
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children ) {
                    StudentInfo studentInfo = child.getValue(StudentInfo.class);
                    studentList.add(new StudentInfo(studentInfo.name, studentInfo.email));
                }
                makeListView(studentList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Ankit","cancelled");

            }
        });



    }

    private void initialization() {
        listView = (ListView) findViewById(R.id.listView);

    }

    public DatabaseReference getDatabaseReference() {

        return NewSessionBottomActivity.getDatabaseReference();
    }

    private void makeListView(ArrayList<StudentInfo> list) {

        StudentAdapter studentAdapter = new StudentAdapter(studentList, StudentListActivity.this);
        listView.setAdapter(studentAdapter);
        progressDialog.hide();
    }
}
