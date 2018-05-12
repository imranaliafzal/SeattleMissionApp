package com.imranaliafzal.seattlemission.seattlemissionapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.imranaliafzal.seattlemission.seattlemissionapp.R;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models.VenueSearchResponse;
import com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private VenuesAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private ProgressBar mProgressBar;

    private TextInputEditText mTextInputEditText;

    private FloatingActionButton mFloatingActionButton;

    private VenueSearchResponse mVenueSearchResponse;

    private Models.VenueSuggest mVenueSuggest;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        mFloatingActionButton = findViewById(R.id.floatingActionButton);

        hideProgress();

        mRecyclerView = findViewById(R.id.venue_list);
        assert mRecyclerView != null;


        mainViewModel = new MainViewModel(getApplication());

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getVenueSearchResponse(this.query).observe(this,
                pVenueSearchResponse -> {
                    mVenueSearchResponse = pVenueSearchResponse;
                    mAdapter = new VenuesAdapter(pVenueSearchResponse.getResponse().getVenues());
                    mRecyclerView.setAdapter(mAdapter);
                   hideProgress();
                });


        mainViewModel.getVenueSearchCompletion(this.query).observe(this,
                pVenueSuggest -> {
                    mVenueSuggest = pVenueSuggest;
                     mAdapter = new VenuesAdapter(pVenueSuggest.getResponse().getMinivenues());
                     mRecyclerView.setAdapter(mAdapter);
                    hideProgress();
                });

        mTextInputEditText = findViewById(R.id.et_query);
        mTextInputEditText.setOnKeyListener((v, keyCode, event) -> {

            if(mTextInputEditText.getText().length()  > 0) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        ) {
                    showProgress();
//              mainViewModel.getVenueSearchCompletion(mTextInputEditText.getText().toString());
                    mainViewModel.getVenueSearchResponse(mTextInputEditText.getText().toString());

                } else if (event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    showProgress();
                    mainViewModel.getVenueSearchResponse(mTextInputEditText.getText().toString());
                }


            }

            return true;

        });

        mFloatingActionButton.setOnClickListener(v -> {

            if(mVenueSearchResponse == null || mVenueSearchResponse.getResponse() == null ||
                    mVenueSearchResponse.getResponse().getVenues() == null ||
                    mVenueSearchResponse.getResponse().getVenues().isEmpty()) {
                return;
            }



            Intent i = MapsActivity.newIntent(this, mVenueSearchResponse);
            startActivity(i);

        });

    }

    private void hideProgress(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgress(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

}
