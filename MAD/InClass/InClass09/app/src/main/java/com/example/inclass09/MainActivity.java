package com.example.inclass09;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
/*
* InClass09
* Trace Path Using Polyline
* Alay Chaniyara
* Adam Burnam
* */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String jsonString;
    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("    Paths Activity");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

       mMap=googleMap;
        getJsonString();
        Gson gson = new Gson();
        Trip getTripPoint=gson.fromJson(jsonString,Trip.class);
        Log.d("Tag",getTripPoint.toString());



        LatLng start = new LatLng(getTripPoint.points.get(0).latitude,getTripPoint.points.get(0).longitude);
        LatLng end = new LatLng(getTripPoint.points.get(getTripPoint.points.size()-1).latitude,getTripPoint.points.get(getTripPoint.points.size()-1).longitude);
         builder=new LatLngBounds.Builder();
        googleMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(5).color(Color.MAGENTA).geodesic(true);
        for (int i = 0; i < getTripPoint.points.size(); i++) {
            LatLng point = new LatLng(getTripPoint.points.get(i).latitude,getTripPoint.points.get(i).longitude);
            builder.include(point );
            options.add(point);
        }

        mMap.addPolyline(options);

        bounds=builder.build();

        mMap.addMarker(new MarkerOptions().position(start).title("Trip Start Point"));
        mMap.addMarker(new MarkerOptions().position(end).title("Trip End Point"));
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,100));
            }
        });


    }


    public void getJsonString(){
        InputStream is = getResources().openRawResource(R.raw.trip);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonString = writer.toString();

    }
}
