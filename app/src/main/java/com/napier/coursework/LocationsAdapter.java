package com.napier.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsHolder> {
    private final List<Location> locations;
    private Context context;
    private int itemResource;

    public LocationsAdapter(Context context, int itemResource, List<Location> locations) {

        this.locations = locations;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public LocationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new LocationsHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(LocationsHolder holder, int position) {

        Location l = this.locations.get(position);

        holder.bindLocation(l);
    }

    @Override
    public int getItemCount() {
        return this.locations.size();
    }
}
