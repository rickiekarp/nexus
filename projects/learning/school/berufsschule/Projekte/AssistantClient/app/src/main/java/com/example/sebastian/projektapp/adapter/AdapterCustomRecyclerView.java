package com.example.sebastian.projektapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebastian.projektapp.R;
import com.example.sebastian.projektapp.model.CardViewItem;

import java.util.ArrayList;

/**
 * Created by sebastian on 04.12.17.
 */

public class AdapterCustomRecyclerView extends RecyclerView.Adapter<AdapterCustomRecyclerView.MyViewHolder> {

    private ArrayList<CardViewItem> items;

    public AdapterCustomRecyclerView(ArrayList<CardViewItem> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_list, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.body.setText(items.get(position).getBody());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView body;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notes_title);
            body = (TextView) itemView.findViewById(R.id.notes_body);
        }
    }

    public String getItemBody(int position) {
        return items.get(position).getBody();
    }

    public String getItemTitle(int position) {
        return items.get(position).getTitle();
    }

    public int getNoteId(int position) {
        return items.get(position).getId();
    }
}
