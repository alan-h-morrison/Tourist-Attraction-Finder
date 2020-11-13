package com.napier.coursework;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.model.DirectionsResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements  OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private AutoCompleteTextView mSearchText;
    private ImageView mCentre;
    private ImageView mOptions;
    private Marker mTempLocation;
    private LatLng current_location;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int ACCESS_REQUEST_LOCATION = 0;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search) ;
        mCentre = (ImageView) findViewById(R.id.ic_centre);
        mOptions = (ImageView) findViewById(R.id.ic_options);

        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        mSearchText.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        getLocation();
        getMapMarkers();
        initialise();

        mMap.setOnInfoWindowClickListener(this);
    }

    private void initialise(){

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE)
                    hideKeyboard();
                    geoLocate();
                return false;
            }
        });

        mCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        mOptions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent optionActivity = new Intent(MapActivity.this, RecyclerActivity.class);
                startActivity(optionActivity);
            }
        });
    }

    private void getLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location != null) {
                    current_location = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.setMyLocationEnabled(true);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((current_location), 13.f));
                }
            }
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider) { }
            public void onProviderDisabled(String provider) { }
        };


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(getResources().getString(R.string.app_name),
                    "No Location Permission. Asking for Permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_REQUEST_LOCATION);
        } else {
            Log.i(getResources().getString(R.string.app_name),
                    "Location is allowed.");
            setLocationUpdateFunction();
        }
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setMessage("Give directions to this location.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void geoLocate(){
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try {

            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(getResources().getString(R.string.app_name), "geoLocate: found a location: " + address.toString());
        }
    }
    private void hideKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted.
                    Log.i(getResources().getString(R.string.app_name),
                            "Location Permission granted by user.");
                    setLocationUpdateFunction();
                    getLocation();
                } else {
                    Log.e(getResources().getString(R.string.app_name),
                            "No Location Permission granted by user.");
                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void setLocationUpdateFunction() {
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
    }

    private void getMapMarkers(){

        LatLng edinburgh_castle = new LatLng(55.9485947,-3.2021022);
        LatLng scott_monument = new LatLng(55.952381,-3.1954628);
        LatLng loch_lomond = new LatLng(56.1557348,-4.8985055);
        LatLng stirling_castle = new LatLng(56.1237904,-3.9496167);
        LatLng ben_nevis = new LatLng(56.7968563,-5.0123053);
        LatLng loch_ness = new LatLng(57.2737239,-4.7818617);
        LatLng edinburgh_oldtown = new LatLng(55.9500926,-3.1980392);
        LatLng palace_holyroodhouse = new LatLng(55.9526737,-3.17484);

        mTempLocation = mMap.addMarker(new MarkerOptions()
                .position(edinburgh_castle)
                .title("Edinburgh Castle")
                .snippet("Castlehill, Edinburgh EH1 2NG")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(scott_monument)
                .title("Scott Monument")
                .snippet("Princes St Gardens, Edinburgh EH2 2EJ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(loch_lomond)
                .title("Loch Lomond")
                .snippet("West Dunbartonshire/Argyll and Bute/Stirling, Scotland")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(stirling_castle)
                .title("Stirling Castle")
                .snippet("Castle Esplanade, Stirling FK8 1EJ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(ben_nevis)
                .title("Ben Nevis")
                .snippet("Lochaber, Highland, Scotland")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(loch_ness)
                .title("Loch Ness")
                .snippet("Scottish Highlands")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(edinburgh_oldtown)
                .title("Edinburgh Oldtown")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(palace_holyroodhouse)
                .title("Palace of Holyroodhouse")
                .snippet("Canongate, Edinburgh EH8 8DX")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mTempLocation.setTag(0);
    }
}


