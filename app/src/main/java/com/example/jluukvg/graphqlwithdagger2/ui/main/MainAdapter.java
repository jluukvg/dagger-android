package com.example.jluukvg.graphqlwithdagger2.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jluukvg.graphqlwithdagger2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import api.SearchByCategoryQuery;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.FeedItemViewHolder> {

    private final List<SearchByCategoryQuery.AsNodePlace> placeEntries;

    MainAdapter() {
        this.placeEntries = new ArrayList<>();
    }

    void addData(List<SearchByCategoryQuery.AsNodePlace> placeEntries) {
        this.placeEntries.addAll(placeEntries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new FeedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.FeedItemViewHolder holder, int position) {

        if (placeEntries.get(position).fieldPlaceAddress() != null) {
            holder.lblItem1.setText(String.format("@%s", Objects.requireNonNull(placeEntries.get(position).fieldPlaceAddress()).addressLine1()));
        } else {
            holder.lblItem1.setText(String.valueOf("No hay dirección"));
        }

        if (placeEntries.get(position).fieldPlaceDescription() != null) {
            holder.lblItem2.setText(String.format("@%s", placeEntries.get(position).fieldPlaceDescription()));
        } else {
            holder.lblItem2.setText(String.valueOf("No hay descripción"));
        }
    }

    @Override
    public int getItemCount() {
        return placeEntries.size();
    }

    class FeedItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblItem1;
        private final TextView lblItem2;

        FeedItemViewHolder(View itemView) {
            super(itemView);
            lblItem1 = itemView.findViewById(R.id.item_1);
            lblItem2 = itemView.findViewById(R.id.item_2);
        }
    }
}
