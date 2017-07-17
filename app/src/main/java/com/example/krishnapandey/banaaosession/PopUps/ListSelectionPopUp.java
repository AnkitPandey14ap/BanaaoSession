package com.example.krishnapandey.banaaosession.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Activities.NewSessionBottomActivity;
import com.example.krishnapandey.banaaosession.Adapters.MyPopUpAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.MySessionData;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.DataClasses.UserInformation;
import com.example.krishnapandey.banaaosession.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ListSelectionPopUp extends AppCompatActivity {

    private ListView listView;
    ArrayList<UserData> list = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<String>();
    int count = 0;

    String caller;
    MyPopUpAdapter myPopUpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection_popup_activity);


        caller = getIntent().getStringExtra("caller");
        listView = (ListView) findViewById(R.id.listView);
        final TextView textView = (TextView) findViewById(R.id.coutTextView);
        final TextView okAction = (Button) findViewById(R.id.okAction);

        fetchList();

        myPopUpAdapter = new MyPopUpAdapter(this, list);
        listView.setAdapter(myPopUpAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData userData = list.get(position);
                if (userData.isSelected()) {
                    nameList.remove(userData.getUserName());
                    userData.setSelected(false);
                    count = count - 1;
                } else {
                    nameList.add(userData.getUserName());
                    userData.setSelected(true);
                    count = count + 1;
                }
                Toast.makeText(ListSelectionPopUp.this, "Total " + count, Toast.LENGTH_SHORT).show();
                textView.setText("add " + count);
                myPopUpAdapter.notifyDataSetChanged();
            }
        });

        okAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caller.equals("student")) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(0, intent);
                    finish();
                } else if (caller.equals("topic")) {
                    Intent intent = new Intent();
//                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    //intent.putExtra("LIST", list);
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(1, intent);
                    finish();
                } else if (caller.equals("trainer")) {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(2, intent);
                    finish();
                }


            }
        });
    }



    private DatabaseReference getRef(String caller) {
        DatabaseReference databaseRef = NewSessionBottomActivity.getDatabaseReference();
        if (caller.equals("student")) {
            databaseRef=databaseRef.child(Nodes.allstudents);
        } else if (caller.equals("topic")) {
            databaseRef=databaseRef.child(Nodes.allTopics);

        } else if (caller.equals("trainer")) {
            databaseRef=databaseRef.child(Nodes.user);

        }
        return databaseRef;

    }

    void fetchList() {
            getRef(caller).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (caller.equals("trainer")) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            UserInformation userInfo = child.getValue(UserInformation.class);
                            list.add(new UserData(false, userInfo.name));
                            Log.i("Ankit", String.valueOf(list));
                        }
                    } else {
                        HashMap<String,String> map= (HashMap<String, String>) dataSnapshot.getValue();
                        map.keySet();
                        for (String key: map.keySet()) {
                            System.out.println("key : " + key);
                            list.add(new UserData(false,map.get(key)));
                        }
                    }

                    myPopUpAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }
}
