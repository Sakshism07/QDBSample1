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

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.MyViewHolder> {
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_listsitem, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final ModelFilterList obj = FilterBusinessProfile.arrayList.get(position);
        holder.tvFilterNames.setText(obj.getName());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(obj.getChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    obj.setChecked(true);
                } else if (!b) {
                    obj.setChecked(false);
                }
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return FilterBusinessProfile.arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tvFilterNames;

        public MyViewHolder(View itemView) {

            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox1);
            tvFilterNames = itemView.findViewById(R.id.tvFilterNames);
        }
    }

}