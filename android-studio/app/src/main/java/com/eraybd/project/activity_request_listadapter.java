package com.eraybd.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class activity_request_listadapter extends ArrayAdapter<activity_request_listdata> {

    private ArrayList<activity_request_listdata> dataArrayList;
    public activity_request_listadapter(@NonNull Context context, ArrayList<activity_request_listdata> dataArrayList) {
        super(context, R.layout.activity_request_list, dataArrayList);
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        activity_request_listdata listData = getItem(position);

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_request_list, parent, false);
        }

        //olusan paketlerin adi burada belirlenir, Support
        TextView listName = view.findViewById(R.id.listName);
        listName.setText(listData.getDonationType().toString());

        return view;
    }
}
