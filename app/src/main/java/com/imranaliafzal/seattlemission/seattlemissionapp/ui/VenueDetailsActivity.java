package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel.VenueDetailsViewModel;

public class VenueDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap gmap;
    Models.Venue venue;
    VenueDetailsViewModel mVenueDetailsViewModel;
    TextView tvName, tvDescription, tvContact, tvLocation, tvVerified, tvUrl, tvHours, tvPopular, tvMenu, tvShortUrl, tvCanonicalUrl;


    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyDzxeuSAWPhkJJhr4iXp-DZYhTWaBkuLE0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);

        tvName = findViewById(R.id.tv_name);
        tvDescription = findViewById(R.id.tv_description);
        tvContact = findViewById(R.id.tv_contact);
        tvLocation = findViewById(R.id.tv_location);
        tvVerified = findViewById(R.id.tv_verified);
        tvUrl = findViewById(R.id.tv_url);
        tvHours = findViewById(R.id.tv_hours);
        tvPopular = findViewById(R.id.tv_popular);
        tvMenu = findViewById(R.id.tv_menu);
        tvShortUrl = findViewById(R.id.tv_short_url);
        tvCanonicalUrl = findViewById(R.id.tv_canonical_url);

        retrieveExtras();

        if(venue == null){
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView lTextView = findViewById(R.id.tv_large_text);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


        mVenueDetailsViewModel = ViewModelProviders.of(this).get(VenueDetailsViewModel.class);
        mVenueDetailsViewModel.getVenueDetailsResponse(venue.getId()).observe(this, pVenueDetailsResponse  -> {
            pVenueDetailsResponse.getName();
            pVenueDetailsResponse.getDescription();
            pVenueDetailsResponse.getContact();
            pVenueDetailsResponse.getLocation();
            pVenueDetailsResponse.getVerified();
            pVenueDetailsResponse.getUrl();
            pVenueDetailsResponse.getHours();
            pVenueDetailsResponse.getPopular();
            pVenueDetailsResponse.getMenu();
            pVenueDetailsResponse.getShortUrl();
            pVenueDetailsResponse.getCanonicalUrl();
        });

        /*FourSquareWebService.getInstance().venueDetails(venue.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.getCause();
            }
        });*/
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

        LatLng lLatLng = new LatLng(Double.valueOf(venue.getLocation().getLat()), Double.valueOf(venue.getLocation().getLng()));
        gmap.addMarker(new MarkerOptions().position(lLatLng).title(venue.getName()));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(lLatLng, 13f));

        GoogleMapOptions lGoogleMapOptions = new GoogleMapOptions().liteMode(true);
        gmap.setMapType(lGoogleMapOptions.getMapType());
    }

    public static Intent newIntent(Context pContext, Models.Venue pVenue){
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
                venue = (Models.Venue) extras.getSerializable("com.imran.ali.afzal.seattlemission.venue");
            }
        }
    }
}
