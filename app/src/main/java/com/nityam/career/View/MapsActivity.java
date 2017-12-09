package com.nityam.career.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nityam.career.Model.FireSave;
import com.nityam.career.R;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        Configuration config = getResources().getConfiguration();
        if (config.orientation == config.ORIENTATION_PORTRAIT) {
            goToMain(this);
        }

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            SupportMapFragment mFrag = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
            mFrag.getMapAsync(this);
        }



//        Toast.makeText(this, Integer.toString(FireSave.getNumberCityMaps()), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        //
    }

    private void goToMain(MapsActivity mapsActivity) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap = googleMap;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

        }

        LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Location l = locManager != null ? locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) : null;

        if (l == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locManager != null ? locManager.getBestProvider(criteria, true) : null;
            assert locManager != null;
            l = locManager.getLastKnownLocation(provider);

                if(l == null){
                    Log.d("<NITYAM>"," setting new locations");
                    l = new Location("");//provider name is unnecessary
                    l.setLatitude(-122.084d);//your coords of course
                    l.setLongitude(37.4220d);
                    l.setAltitude(0.0d);
                }

                googleMap.addMarker(new MarkerOptions().position(
                        new LatLng(l.getLatitude(), l.getLongitude())).title("Current location"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(l.getLatitude(), l.getLongitude()), 16));

//                  isFirstLaunch = false;
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(
//                        new LatLng(l.getLatitude(), l.getLongitude())));


        } else {
                googleMap.addMarker(new MarkerOptions().position(
                        new LatLng(l.getLatitude(), l.getLongitude())).title("Your location"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(l.getLatitude(), l.getLongitude()), 16));

//                isFirstLaunch = false;
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(
//                        new LatLng(l.getLatitude(), l.getLongitude())));
        }


        try {
            int totalLocations = FireSave.getNumberCityMaps();
            if(totalLocations < 1) return;

            for(int i =0; i< totalLocations; i++){
                String[] info = FireSave.getMapAt(i);

                double[] loca = geoLocate(info[1]);
                if(loca!= null)
                    googleMap.addMarker(new MarkerOptions().position(
                            new LatLng(loca[0], loca[1])).title(info[0]));
                else
                    Log.d("<LOCAITONnityam>","not found");
            }

        } catch (IOException e) {
            Log.d("ERROR", "ERROR");
            e.printStackTrace();
        }
    }

    private double[] geoLocate(String city) throws IOException {

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(city, 5);
        if(list.size()<1) return null;

        Address add = list.get(0);
        String locality = add.getLocality();

//        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        double[] loca = {lat, lng};

        return loca;

    }

    private void addMarker(double lat, double lng) {

        LatLng latLng = new LatLng(lat, lng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        // Placing a marker on the touched position
//        googleMap.addMarker(markerOptions);

    }

}
