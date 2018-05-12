package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models.VenueSearchResponse;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    private VenueSearchResponse mVenueSearchResponse;

    public static Intent newIntent(Context pContext, VenueSearchResponse pVenueSearchResponse) {
        Intent i = new Intent(pContext, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("com.imran.ali.afzal.seattlemission.venuesearchresponse",
                pVenueSearchResponse);
        i.putExtras(bundle);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        retrieveExtras();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void retrieveExtras() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            if (
                    extras.containsKey(
                            "com.imran.ali.afzal.seattlemission.venuesearchresponse")
                    ) {
                this.mVenueSearchResponse = (VenueSearchResponse) extras.getSerializable(
                        "com.imran.ali.afzal.seattlemission.venuesearchresponse");
            }
        }
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
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);

        if (mVenueSearchResponse == null) {
            retrieveExtras();
        }

        List<Models.Venue> lVenues = this.mVenueSearchResponse.getResponse().getVenues();
        for (Models.Venue v : lVenues) {

            LatLng lLatLng = new LatLng(Double.valueOf(v.getLocation().getLat()),
                    Double.valueOf(v.getLocation().getLng()));
            mMap.addMarker(new MarkerOptions().position(lLatLng).title(v.getName())).setTag(v);
        }

        Double lat = Double.valueOf(mVenueSearchResponse.getResponse().getGeocode().getFeature()
                .getGeometry().getCenter().getLat());
        Double lng = Double.valueOf(mVenueSearchResponse.getResponse().getGeocode().getFeature()
                .getGeometry().getCenter().getLng());
        LatLng centerLatLng = new LatLng(lat, lng);
        String tit = mVenueSearchResponse.getResponse().getGeocode().getFeature().getName();

        mMap.addMarker(new MarkerOptions().position(centerLatLng).title(tit)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, 14f));
    }

    @Override
    public void onInfoWindowClick(Marker pMarker) {
        if (pMarker.getTag() != null) {
            Models.Venue lVenue = (Models.Venue) pMarker.getTag();
            Intent lIntent = VenueDetailsActivity.newIntent(this, lVenue);
            startActivity(lIntent);
        }
    }

}
