package com.example.krishnapandey.banaaosession.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.krishnapandey.banaaosession.R;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
private ArrayList<String> list = new ArrayList<String>();
private Context context;



public MyCustomAdapter(ArrayList<String> list, Context context) {
    this.list = list;
    Log.i("Ankit", "Size " + list.size());
    this.context = context;
}

@Override
public int getCount()
{
    return list.size();
}

@Override
public Object getItem(int pos) {
    return list.get(pos);
}

@Override
public long getItemId(int pos) {
    return 0;
    //just return 0 if your list items do not have an Id variable.
}

@Override
public View getView(final int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //view = inflater.inflate(R.layout.item_view, null);
        view = inflater.inflate(R.layout.item_view, null);
    }

    //Handle TextView and display string from your list
    TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
    listItemText.setText(list.get(position));
    Log.i("Ankit","name" +list.get(position));

    //Handle buttons and add onClickListeners
    ImageView deleteBtn = (ImageView)view.findViewById(R.id.delete_btn);
    //Button addBtn = (Button)view.findViewById(R.id.add_btn);

    deleteBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //do something
            list.remove(position); //or some other task
            notifyDataSetChanged();
        }
    });
    /*addBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //do something
            notifyDataSetChanged();
        }
    });
*/
    return view;
}

}