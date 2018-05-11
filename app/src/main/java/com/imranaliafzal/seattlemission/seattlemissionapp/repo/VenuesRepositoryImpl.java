package com.imranaliafzal.seattlemission.seattlemissionapp.repo;

import android.arch.lifecycle.MutableLiveData;

import com.imranaliafzal.seattlemission.seattlemissionapp.api.FourSquareWebService;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models.VenueSearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 */
public class VenuesRepositoryImpl implements VenuesRepository {

    private static final String CLIENT_ID = "TU0CMRZXRNOFPSWADXGMMGRNJS4BEDXKA3LS10UFVEJC5ANM";

    private static final String CLIENT_SECRET = "MO4NDMDXMLXSBLZEPF0EI32TSSW3PN4TR4V40ZX501II4ZNC";

    private static final String NEAR = "Seattle,+WA";

    private static final String V = "20180401";

    private static final Integer LIMIT = 100;

    private MutableLiveData<VenueSearchResponse> mVenueSearchResponseMutableLiveData;

    @Override
    public void venueSearch(String query) {

        Call<VenueSearchResponse> lSearchResponseCall = FourSquareWebService.getInstance().searchVenue(CLIENT_ID, CLIENT_SECRET, NEAR,query, V, LIMIT);
        lSearchResponseCall.enqueue(new Callback<VenueSearchResponse>() {
            @Override
            public void onResponse(Call<VenueSearchResponse> call, Response<VenueSearchResponse> response) {
                response.isSuccessful();
                mVenueSearchResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VenueSearchResponse> call, Throwable t) {
                t.getCause();
            }
        });
    }

}
