package com.example.user.mytodolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    public ToDoAdapter(@NonNull Context context, ArrayList<ToDo> list) {
        super(context, 0, list);

    }

    @Override
    public View getView(int position, View listItemView, ViewGroup parent){
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);
        }

        ToDo current= getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(current.getTitle().toString());

        TextView number = (TextView) listItemView.findViewById(R.id.number);
        number.setText(current.getNumber().toString());

        listItemView.setTag(current);

        return listItemView;

    }
}
