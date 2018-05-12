package com.imranaliafzal.seattlemission.seattlemissionapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SeattleMissionApp
 * <p>
 * Created by iafzal on 5/11/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 *
 *
 *
 */
public class VenueDetailsViewModel extends AndroidViewModel {

    MutableLiveData<Models.VenueDetailsResponse> mVenueDetailsResponseMutableLiveData;

    public VenueDetailsViewModel(@NonNull Application application) {
        super(application);
        mVenueDetailsResponseMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Models.VenueDetailsResponse> getVenueDetailsResponse(String venue_id) {
        if (venue_id.isEmpty()) {
            return mVenueDetailsResponseMutableLiveData;
        }

        FourSquareWebService.getInstance().venueDetails(venue_id, Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, Constants.V)
                .enqueue(new Callback<Models.VenueDetailsResponse>() {
                    @Override
                    public void onResponse(Call<Models.VenueDetailsResponse> call,
                                           Response<Models.VenueDetailsResponse> response) {
                        mVenueDetailsResponseMutableLiveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Models.VenueDetailsResponse> call, Throwable t) {
                        t.getCause();
                    }
                });

        return mVenueDetailsResponseMutableLiveData;
    }


}
