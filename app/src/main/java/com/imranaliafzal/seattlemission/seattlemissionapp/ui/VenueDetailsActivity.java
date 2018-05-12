package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.utils.Constants;
import com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel.VenueDetailsViewModel;

/**
 *
 */

public class VenueDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gmap;
    Models.Venue venue;
    VenueDetailsViewModel mVenueDetailsViewModel;
    TextView tvName, tvDescription, tvContact, tvLocation, tvVerified, tvUrl, tvHours, tvPopular,
            tvMenu, tvShortUrl, tvCanonicalUrl;

    public static Intent newIntent(Context pContext, Models.Venue pVenue) {
        Intent i = new Intent(pContext, VenueDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("com.imran.ali.afzal.seattlemission.venue", pVenue);
        i.putExtras(bundle);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        retrieveExtras();

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getView().setClickable(false);
        mapFragment.getMapAsync(this);

        mVenueDetailsViewModel = new VenueDetailsViewModel(getApplication());

        mVenueDetailsViewModel = ViewModelProviders.of(this).get(
                VenueDetailsViewModel.class);
        mVenueDetailsViewModel.getVenueDetailsResponse(venue.getId()).observe(this,
                pVenueDetailsResponse -> {
                    Models.Venue v = venue = pVenueDetailsResponse.getResponse().getVenue();
                    if (v.getName() != null) {
                        tvName.setText("Name: " + v.getName());
                    }

                    if (v.getHours() != null && v.getHours().getStatus() != null) {
                        tvHours.setText("Hours: " + v.getHours().getStatus());
                    }

                    if (v.getLocation() != null && v.getLocation().getFormattedAddress() != null) {
                        tvLocation.setText(""+ v.getLocation().getFormattedAddress());
                    }

                    if (v.getCanonicalUrl() != null) {
                        String url = v.getCanonicalUrl();
                        tvCanonicalUrl.setText(url);
                    }

                });

        mVenueDetailsViewModel.getVenueDetailsResponse(venue.getId());

        tvCanonicalUrl.setOnClickListener(v -> {
            if (!venue.getCanonicalUrl().isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(venue.getCanonicalUrl()));
                startActivity(browserIntent);
                finish();
            }
        });
    }

    private void retrieveExtras() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras.containsKey("com.imran.ali.afzal.seattlemission.venue")) {
                venue = (Models.Venue) extras.getSerializable(
                        "com.imran.ali.afzal.seattlemission.venue");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        LatLng lLatLng = new LatLng(Double.valueOf(venue.getLocation().getLat()),
                Double.valueOf(venue.getLocation().getLng()));
        gmap.addMarker(new MarkerOptions().position(lLatLng).title(venue.getName()));
        //Adding marker for SEATTLE CENTER
        LatLng centerLatLng = new LatLng(Constants.SEATTLE_CENTER_LAT, Constants.SEATTLE_CENTER_LNG);
        gmap.addMarker(new MarkerOptions().position(centerLatLng).title("Seattle")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(lLatLng, 13f));

    }
}
