package com.example.krishnapandey.banaaosession.PopUps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Adapters.MyPopUpAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;

public class TopicPopUp extends AppCompatActivity {

    private BottomNavigationView navigation;
    private ListView completedListView;
    private ListView incompletedListView;
    ArrayList<String> completeList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_pop_up);

        initialize();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        makeCompletedListView(completeList);

    }

    private void makeCompletedListView(ArrayList<String> completeList) {
        final ArrayList<UserData> list = new ArrayList<>();

        list.add(new UserData(false, "Ankit"));
        list.add(new UserData(false, "krishna"));
        list.add(new UserData(false, "madhav"));
        list.add(new UserData(false, "mayank"));


        final MyPopUpAdapter myPopUpAdapter = new MyPopUpAdapter(this, list);
        completedListView.setAdapter(myPopUpAdapter);
        completedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData userData = list.get(position);
                if (userData.isSelected()) {
                    //nameList.remove(userData.getUserName());
                    userData.setSelected(false);
                    //count = count - 1;
                } else {
                    //nameList.add(userData.getUserName());
                    userData.setSelected(true);
                    //count = count + 1;
                }
                Toast.makeText(TopicPopUp.this, "Total ", Toast.LENGTH_SHORT).show();
                //textView.setText("add "+count);
                myPopUpAdapter.notifyDataSetChanged();
            }
        });


    }

    private void initialize() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        incompletedListView = (ListView) findViewById(R.id.incompletedListView);
        completedListView = (ListView) findViewById(R.id.completedListView);

        completeList = new ArrayList<>();
        completeList.add("ankit");
        completeList.add("ankit");
        completeList.add("ankit");

    }

}
