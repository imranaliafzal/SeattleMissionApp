package com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models.VenueSearchResponse;
import com.imranaliafzal.seattlemission.seattlemissionapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 */
public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<VenueSearchResponse> mSearchResponseLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mSearchResponseLiveData = new MutableLiveData<>();
    }

    public LiveData<VenueSearchResponse> getVenueSearchResponse(String query){

        Call<VenueSearchResponse> lSearchResponseCall = FourSquareWebService.getInstance().searchVenue(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.NEAR,query, Constants.V, Constants.LIMIT);
        lSearchResponseCall.enqueue(new Callback<VenueSearchResponse>() {
            @Override
            public void onResponse(Call<VenueSearchResponse> call, Response<VenueSearchResponse> response) {
                response.isSuccessful();

                mSearchResponseLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VenueSearchResponse> call, Throwable t) {
                t.getCause();
            }
        });

        return mSearchResponseLiveData;
    }








}
