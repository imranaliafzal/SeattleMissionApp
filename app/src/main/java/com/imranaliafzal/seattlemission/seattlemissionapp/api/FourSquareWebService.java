package com.imranaliafzal.seattlemission.seattlemissionapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;
import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models.VenueSearchResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 *
 * Concrete Implementation of the API Interface. It has factory methods and other
 * static final params.
 *
 *
 */
public class FourSquareWebService implements WebService {
    private static final String baseUrl = "https://api.foursquare.com";
    private static final FourSquareWebService ourInstance = new FourSquareWebService();
    private WebService webService;

    private FourSquareWebService() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder();

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        webService = retrofit.create(WebService.class);
    }

    public static FourSquareWebService getInstance() {
        return ourInstance;
    }

    @Override
    public Call<Models.VenuePhotosResponse> fetchPhotoList(String venue_id,
                                                           String client_id, String client_secret,
                                                           String version, String group,
                                                           Integer limit, Integer offset) {
        return webService.fetchPhotoList(venue_id, client_id, client_secret, version, group,
                limit, offset);
    }

    @Override
    public Call<ResponseBody> fetchPhoto(String prefix, String suffix) {
        return webService.fetchPhoto(prefix, suffix);
    }

    @Override
    public Call<ResponseBody> fetchImage(String prefix, String suffix) {
        return webService.fetchImage(prefix, suffix);
    }

    @Override
    public Call<VenueSearchResponse> searchVenue(String client_id, String client_secret,
                                                 String near, String query, String version,
                                                 int limit) {
        return webService.searchVenue(client_id, client_secret, near, query, version, limit);
    }

    @Override
    public Call<Models.VenueDetailsResponse> venueDetails(String venue_id, String client_id,
                                                          String client_secret,
                                                          String version) {
        return webService.venueDetails(venue_id, client_id, client_secret, version);
    }

    @Override
    public Call<Models.VenueSuggest> searchCompletion(String client_id, String client_secret, String query, String near, String version, int limit) {
        return webService.searchCompletion(client_id, client_secret, query, near, version, limit);
    }

}
