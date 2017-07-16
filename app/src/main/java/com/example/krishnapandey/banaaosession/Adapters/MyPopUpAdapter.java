package com.example.krishnapandey.banaaosession.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.krishnapandey.banaaosession.DataClasses.UserData;
import com.example.krishnapandey.banaaosession.R;

import java.util.List;

/**
 * Created by krishna pandey on 12-07-2017.
 */

public class MyPopUpAdapter extends BaseAdapter implements ListAdapter {
    private final Context context;
    private final List<UserData> list;

    LayoutInflater layoutInflater;
    public MyPopUpAdapter(Context context, List<UserData> list) {
        this.context = context;
        this.list = list;
        //layoutInflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.popup_item_view,null);
        }
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(list.get(position).getUserName());
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        UserData userData = list.get(position);
        if (userData.isSelected()) {
            imageView.setImageResource(R.drawable.checked_box);
        }else
            imageView.setImageResource(R.drawable.check_box);
        return view;
    }
}
