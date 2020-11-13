package com.napier.coursework;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Location location =
                (Location) getIntent().getParcelableExtra("location");

        if (location != null) {
            ImageView locationImage =
                    (ImageView) findViewById(R.id.image);
            TextView locationAddress =
                    (TextView) findViewById(R.id.address);
            TextView opening_times =
                    (TextView) findViewById(R.id.opening_times);
            TextView locationInfo =
                    (TextView) findViewById(R.id.info);

            locationImage.setImageResource(location.getIconId());
            locationAddress.setText(location.getAddress());
            opening_times.setText(location.getOpeningTimes());
            locationInfo.setText(location.getInfo());
        }
    }
}
