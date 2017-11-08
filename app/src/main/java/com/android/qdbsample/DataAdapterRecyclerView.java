package com.android.qdbsample;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Android on 04-Nov-17.
 */

public class DataAdapterRecyclerView extends RecyclerView.Adapter<DataAdapterRecyclerView.MyViewHolder> {

    ArrayList<GetSet> arrayList = new ArrayList<>();
    Context context;

    public DataAdapterRecyclerView(ArrayList<GetSet> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public DataAdapterRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybusiness_itemlist, parent, false);

        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetSet getSet = arrayList.get(position);
        holder.tvMyBusinessName.setText(getSet.getName());
        String imgurl = getSet.getIngurl();

//        Picasso.with(holder.itemView.getContext())
//                .load(imgurl)
//                .into(holder.ivMyBusinessProfilelogo);

        //holder.ivMyBusinessProfilelogo.setImageURI(Uri.parse(getSet.getIngurl()));

        Glide.with(holder.itemView.getContext()).load(imgurl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivMyBusinessProfilelogo);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMyBusinessProfilelogo;
        TextView tvMyBusinessName;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivMyBusinessProfilelogo = itemView.findViewById(R.id.ivMyBusinessProfileLogo);
            tvMyBusinessName = itemView.findViewById(R.id.tvMyBusinessName);
        }
    }
}
