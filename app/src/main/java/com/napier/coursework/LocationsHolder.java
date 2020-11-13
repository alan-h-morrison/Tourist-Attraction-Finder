package com.napier.coursework;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LocationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView locationName;

    private Location location;
    private Context context;

    public LocationsHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        this.locationName = (TextView) itemView.findViewById(R.id.entry_location_name);

        itemView.setOnClickListener(this);
    }

    public void bindLocation(Location location) {
        this.location = location;
        this.locationName.setText(location.getName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
        intent.putExtra("location", this.location);
        itemView.getContext().startActivity(intent);

    }
}
