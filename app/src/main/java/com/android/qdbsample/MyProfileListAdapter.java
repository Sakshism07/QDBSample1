package com.android.qdbsample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 03-Nov-17.
 */

public class MyProfileListAdapter extends BaseAdapter {

    ArrayList<String> strArrayNames = new ArrayList<>();

    public MyProfileListAdapter(ArrayList<String> strArrayNames) {
        this.strArrayNames = strArrayNames;
    }

    @Override
    public int getCount() {
        return strArrayNames.size();
    }

    @Override
    public Object getItem(int position) {
        return strArrayNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.myprofilelist_items, parent, false);

        String text = strArrayNames.get(position);
        TextView makerID = (TextView) view.findViewById(R.id.tvMyProfileDetails);
        makerID.setText(text);

        return view;
    }
}
