package com.example.krishnapandey.banaaosession.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.krishnapandey.banaaosession.DataClasses.MySessionData;
import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;

/**
 * Created by krishna pandey on 16-07-2017.
 */

public class MySessionAdapter extends BaseAdapter implements ListAdapter {

    private final ArrayList<MySessionData> sessionList;
    private final Context context;

    public MySessionAdapter(ArrayList<MySessionData> sessionList, Context context) {

        this.sessionList = sessionList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sessionList.size();
    }

    @Override
    public Object getItem(int position) {
        return sessionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = inflater.inflate(R.layout.item_view, null);
            view = inflater.inflate(R.layout.my_session_item, null);
        }
        TextView sessionName= (TextView) view.findViewById(R.id.sessionName);
        TextView timming= (TextView) view.findViewById(R.id.timming);
        TextView dateTextView= (TextView) view.findViewById(R.id.dateTextView);

        sessionName.setText(position+1+". "+sessionList.get(position).getSessionName());
        timming.setText("       From   "+sessionList.get(position).getTimeFrom()+"     To   "+sessionList.get(position).getTimeTo());
        dateTextView.setText("       Date : "+sessionList.get(position).getDate());

        return view;
    }
}
