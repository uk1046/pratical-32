package com.example.practical32;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String API_KEY = "YOUR_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng startLocation = new LatLng(START_LATITUDE, START_LONGITUDE); // Replace with start latitude and longitude
        LatLng endLocation = new LatLng(END_LATITUDE, END_LONGITUDE); // Replace with end latitude and longitude
        mMap.addMarker(new MarkerOptions().position(startLocation).title("Start"));
        mMap.addMarker(new MarkerOptions().position(endLocation).title("End"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 10));

        drawRoute(startLocation, endLocation);
    }

    private void drawRoute(LatLng startLocation, LatLng endLocation) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DirectionsApi.newRequest(context)
                .origin(new com.google.maps.model.LatLng(startLocation.latitude, startLocation.longitude))
                .destination(new com.google.maps.model.LatLng(endLocation.latitude, endLocation.longitude))
                .mode(TravelMode.DRIVING)
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {

                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Toast.makeText(MainActivity.this, "Failed to fetch directions", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
