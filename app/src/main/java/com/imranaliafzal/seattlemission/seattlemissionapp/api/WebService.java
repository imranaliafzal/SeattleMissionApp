package com.imranaliafzal.seattlemission.seattlemissionapp.api;


import com.imranaliafzal.seattlemission.seattlemissionapp.model.VenueSearchResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * HomeAwayFromHome
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Spendlabs Inc. All rights reserved.
 */
public interface WebService {

    @GET("/v2/venues/{venue_id}/photos")
    Call<VenueSearchResponse.VenuePhotosResponse> fetchPhotoList(@Path("venue_id") String venue_id, @Query("client_id") String client_id,
                                                                 @Query("client_secret") String client_secret,
                                                                 @Query("v") String version, @Query("group") String group, @Query("limit") Integer limit, @Query("offset") Integer offset);


    @GET("{prefix}300x500{suffix}")
    Call<ResponseBody> fetchPhoto(@Path(value = "prefix", encoded = true) String prefix, @Path(value = "suffix", encoded = true) String suffix);

    @GET("{prefix}bg_88{suffix}")
    Call<ResponseBody> fetchImage(
            @Path(value = "prefix", encoded = true) String prefix,
            @Path(value = "suffix", encoded = true) String suffix);


    @GET("/v2/venues/search")
    Call<VenueSearchResponse> searchVenue(@Query("client_id") String client_id,
                                          @Query("client_secret") String client_secret,
                                          @Query("near") String near,
                                          @Query("query") String query,
                                          @Query("v") String version,
                                          @Query("limit") int limit
    );

    @GET("/v2/venues/{venue_id}")
    Call<ResponseBody> venueDetails(@Path("venue_id") String venue_id);


}