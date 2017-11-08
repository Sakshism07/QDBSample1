package com.android.qdbsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 06-Nov-17.
 */

public class FilterListAdapter extends BaseAdapter {

    ArrayList<String> arrayList = new ArrayList<>();

    public FilterListAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tvFilterNames;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        String text = null;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.filter_listsitem, parent, false);
            viewHolder = new ViewHolder();

            text = arrayList.get(position);

            viewHolder.tvFilterNames = convertView.findViewById(R.id.tvFilterNames);
            viewHolder.checkBox = convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);
//
//            view.setTag(convertView);
//
//            tvFilterNames.setText(text);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    int position = (Integer) buttonView.getTag();

                    //.get(position).setChecked(buttonView.isChecked());

                }
            });

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvFilterNames.setText(text);
        viewHolder.checkBox.setTag(position);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;

    }
}
