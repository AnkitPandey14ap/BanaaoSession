package com.example.krishnapandey.banaaosession.PopUps;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Adapters.MyPopUpAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;
import java.util.List;

public class ListSelectionPopUp extends AppCompatActivity {

    private ListView listView;
    ArrayList<UserData> list = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<String>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection_popup_activity);
        final String caller = getIntent().getStringExtra("caller");

        listView = (ListView) findViewById(R.id.listView);
        final TextView textView = (TextView) findViewById(R.id.coutTextView);
        final TextView okAction = (Button) findViewById(R.id.okAction);

        list.add(new UserData(false, "Ankit"));
        list.add(new UserData(false, "krishna"));
        list.add(new UserData(false, "madhav"));
        list.add(new UserData(false, "mayank"));
        final MyPopUpAdapter myPopUpAdapter = new MyPopUpAdapter(this, list);
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
                Toast.makeText(ListSelectionPopUp.this, "Total "+count, Toast.LENGTH_SHORT).show();
                textView.setText("add "+count);
                myPopUpAdapter.notifyDataSetChanged();
            }
        });

        okAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caller.equals("student")) {
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(0,intent);
                    finish();
                }else if (caller.equals("topic")) {
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(1,intent);
                    finish();
                }else if (caller.equals("trainer")) {
                    Intent intent=new Intent();
                    intent.putStringArrayListExtra("NAME LIST", nameList);
                    setResult(2,intent);
                    finish();
                }



            }
        });
    }

}
