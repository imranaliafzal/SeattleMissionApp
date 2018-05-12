package com.imranaliafzal.seattlemission.seattlemissionapp.api;


import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 *
 * Interface to talk to FourSquare REST API server
 *
 */
public interface WebService {

    /**
     *
     * Gets a list of photos for a venue from FourSquare
     *
     * @param venue_id
     * @param client_id
     * @param client_secret
     * @param version
     * @param group
     * @param limit
     * @param offset
     * @return
     *
     */
    @GET("/v2/venues/{venue_id}/photos")
    Call<Models.VenuePhotosResponse> fetchPhotoList(
            @Path("venue_id") String venue_id, @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("v") String version,
            @Query("group") String group, @Query("limit") Integer limit,
            @Query("offset") Integer offset);


    /**
     * Fetches a specific photo of the venue from the server.
     *
     * @param prefix
     * @param suffix
     * @return
     */
    @GET("{prefix}300x500{suffix}")
    Call<ResponseBody> fetchPhoto(@Path(value = "prefix", encoded = true) String prefix,
                                  @Path(value = "suffix", encoded = true) String suffix);

    /**
     * Retrieves the image of a category icon from Foursquare
     *
     * @param prefix
     * @param suffix
     * @return
     */
    @GET("{prefix}bg_88{suffix}")
    Call<ResponseBody> fetchImage(
            @Path(value = "prefix", encoded = true) String prefix,
            @Path(value = "suffix", encoded = true) String suffix);

    /**
     * Fetches a list of venues matching the criteria below from the foursquare host.
     * @param client_id
     * @param client_secret
     * @param near
     * @param query
     * @param version
     * @param limit
     * @return
     */
    @GET("/v2/venues/search")
    Call<Models.VenueSearchResponse> searchVenue(@Query("client_id") String client_id,
                                                 @Query("client_secret") String client_secret,
                                                 @Query("near") String near,
                                                 @Query("query") String query,
                                                 @Query("v") String version,
                                                 @Query("limit") int limit
    );

    /**
     * Gets the details of a specific venue from foursquare. It is used to display data in the
     * details view
     *
     * @param venue_id
     * @param client_id
     * @param client_secret
     * @param version
     * @return
     */
    @GET("/v2/venues/{venue_id}")
    Call<Models.VenueDetailsResponse> venueDetails(@Path("venue_id") String venue_id,
                                                   @Query("client_id") String client_id,
                                                   @Query("client_secret") String client_secret,
                                                   @Query("v") String version
    );

    /**
     * Returns shorter version of venue list to display search results and the user types the query
     *
     * @param client_id
     * @param client_secret
     * @param query
     * @param near
     * @param version
     * @param limit
     * @return
     */
    @GET("/v2/venues/suggestcompletion")
    Call<Models.VenueSuggest> searchCompletion(@Query("client_id") String client_id,
                                               @Query("client_secret") String client_secret,
                                               @Query("query") String query,
                                               @Query("near") String near,
                                               @Query("v") String version,
                                               @Query("limit") int limit
    );


}
