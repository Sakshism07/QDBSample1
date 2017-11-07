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


    ArrayList<ModelFilterList> arrayList = new ArrayList<>();


    public FilterListAdapter(ArrayList<ModelFilterList> arrayList) {
        this.arrayList = arrayList;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_listsitem, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelFilterList obj = arrayList.get(position);
        holder.tvFilterNames.setText(obj.getName());
        holder.checkBox.setChecked(obj.getChecked());

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
        return arrayList.size();
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
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    private class ViewHolder {
//        TextView tvFilterNames;
//        CheckBox checkBox;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        final ViewHolder viewHolder;
//        String text = null;
//
//        if (convertView == null) {
//
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            convertView = inflater.inflate(R.layout.filter_listsitem, parent, false);
//            viewHolder = new ViewHolder();
//
//            text = arrayList.get(position);
//
//            viewHolder.tvFilterNames = convertView.findViewById(R.id.tvFilterNames);
//            viewHolder.checkBox = convertView.findViewById(R.id.checkbox);
//
//            convertView.setTag(viewHolder);
////
////            view.setTag(convertView);
////
////            tvFilterNames.setText(text);
//            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    int position = (Integer) buttonView.getTag();
//
//                    //.get(position).setChecked(buttonView.isChecked());
//
//                }
//            });
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.tvFilterNames.setText(text);
//        viewHolder.checkBox.setTag(position);
//        return convertView;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//
//    }
}
