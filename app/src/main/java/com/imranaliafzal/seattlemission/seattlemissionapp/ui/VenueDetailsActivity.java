package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.VenueSearchResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap gmap;
    VenueSearchResponse.Venue venue;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyDzxeuSAWPhkJJhr4iXp-DZYhTWaBkuLE0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        findViewById(R.layout.content_venue_details);
        TextView lTextView = findViewById(R.id.tv_large_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        retrieveExtras();

        FourSquareWebService.getInstance().venueDetails(venue.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getCause();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        gmap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f));

        GoogleMapOptions lGoogleMapOptions = new GoogleMapOptions().liteMode(true);
        gmap.setMapType(lGoogleMapOptions.getMapType());
    }

    public static Intent newIntent(Context pContext, VenueSearchResponse.Venue pVenue){
        Intent i = new Intent(pContext, VenueDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("com.imran.ali.afzal.seattlemission.venue", pVenue);
        i.putExtras(bundle);
        return i;
    }

    private void retrieveExtras() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras.containsKey("com.imran.ali.afzal.seattlemission.venue")) {
                venue = (VenueSearchResponse.Venue) extras.getSerializable("com.imran.ali.afzal.seattlemission.venue");
            }
        }
    }
}
