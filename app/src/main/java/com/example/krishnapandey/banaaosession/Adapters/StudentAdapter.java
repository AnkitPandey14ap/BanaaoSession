package com.example.krishnapandey.banaaosession.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.krishnapandey.banaaosession.DataClasses.StudentInfo;
import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;

/**
 * Created by krishna on 11/8/17.
 */

public class StudentAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<StudentInfo> list;
    Context context;

    public StudentAdapter(ArrayList<StudentInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = inflater.inflate(R.layout.item_view, null);
            view = inflater.inflate(R.layout.student_list_item_layout, null);
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView emailTextView = (TextView) view.findViewById(R.id.emailTextView);

        nameTextView.setText(+position+1+". "+list.get(position).getName());
        emailTextView.setText(list.get(position).getEmail());


        return view;
    }
}
