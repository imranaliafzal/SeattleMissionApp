package com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
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
 *
 * ViewModel for MainActivity
 */
public class MainViewModel extends AndroidViewModel {


    /**
     * LiveData to hold search response from api
     * It is observed by the Main Activity
     */
    private MutableLiveData<Models.VenueSearchResponse> mSearchResponseLiveData;

    /**
     * LiveData to MiniVenues returned from four square.
     * It is observed by the Main Activity
     *
     */
    private MutableLiveData<Models.VenueSuggest> mSuggestMutableLiveData;

    /**
     * Constructor create - ViewModel is instantiated in the createActivity method of MainActivity
     * @param application
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        mSearchResponseLiveData = new MutableLiveData<>();
        mSuggestMutableLiveData = new MutableLiveData<>();
    }

    /**
     * Returns the reference to suggest Api response. Since the apai
     * @param query
     * @return
     */
    public LiveData<Models.VenueSuggest> getVenueSearchCompletion(String query) {

        if (query == null || query.isEmpty()) {
            return mSuggestMutableLiveData;
        }

        Call<Models.VenueSuggest> lSearchResponseCall =
                FourSquareWebService.getInstance().searchCompletion(Constants.CLIENT_ID,
                        Constants.CLIENT_SECRET, query, Constants.NEAR, Constants.V, Constants.LIMIT);

        lSearchResponseCall.enqueue(new Callback<Models.VenueSuggest>() {
            @Override
            public void onResponse(Call<Models.VenueSuggest> call,
                                   Response<Models.VenueSuggest> response) {
                if (response.isSuccessful()) {

                    mSuggestMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Models.VenueSuggest> call, Throwable t) {
                t.getCause();
            }
        });

        return mSuggestMutableLiveData;
    }


    public LiveData<VenueSearchResponse> getVenueSearchResponse(String query) {

        if (query == null || query.isEmpty()) {
            return mSearchResponseLiveData;
        }

        Call<VenueSearchResponse> lSearchResponseCall =
                FourSquareWebService.getInstance().searchVenue(Constants.CLIENT_ID,
                        Constants.CLIENT_SECRET, Constants.NEAR, query, Constants.V,
                        Constants.LIMIT);
        lSearchResponseCall.enqueue(new Callback<VenueSearchResponse>() {
            @Override
            public void onResponse(Call<VenueSearchResponse> call,
                                   Response<VenueSearchResponse> response) {
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
