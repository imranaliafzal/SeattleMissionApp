package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.VenueSearchResponse;
import com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private VenuesAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;

//    private FloatingActionButton mFloatingActionButton;

    private VenueSearchResponse mVenueSearchResponse;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieveExtras();

        mProgressBar = findViewById(R.id.progress_bar);
//        mFloatingActionButton = findViewById(R.id.floatingActionButton);

        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.venue_list);
        assert mRecyclerView != null;

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getVenueSearchResponse(this.query).observe(this, pVenueSearchResponse -> {
            mVenueSearchResponse = pVenueSearchResponse;
            mAdapter = new VenuesAdapter(pVenueSearchResponse.getResponse().getVenues());
            mRecyclerView.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
        });

        /*mFloatingActionButton.setOnClickListener(v -> {
            Intent i = MapsActivity.newIntent(this, mVenueSearchResponse);
            startActivity(i);
        });*/

    }

    private void retrieveExtras(){
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras.containsKey("com.imran.ali.afzal.seattlemission.query")) {
                query = extras.getString("com.imran.ali.afzal.seattlemission.query");
            }
        }
    }

    public static Intent newIntent(Context pContext, String query){
        Intent i = new Intent(pContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("com.imran.ali.afzal.seattlemission.query", query);
        i.putExtras(bundle);
        return i;
    }
}
