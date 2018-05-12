package com.imranaliafzal.seattlemission.seattlemissionapp.utils;

import android.location.Location;

import com.imranaliafzal.seattlemission.seattlemissionapp.model.Models;

import java.text.DecimalFormat;
import java.util.List;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/11/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 */
public class Util {

    static final DecimalFormat df = new DecimalFormat("#.##");

    static Location SEATTLE_CENTER_LOCATION;

    static {
        SEATTLE_CENTER_LOCATION = new Location("");
        SEATTLE_CENTER_LOCATION.setLatitude(Constants.SEATTLE_CENTER_LAT);
        SEATTLE_CENTER_LOCATION.setLongitude(Constants.SEATTLE_CENTER_LNG);
    }

    public static String categoryListToDisplay(List<Models.Category> pCategoryList) {
        String result = "";
        for (Models.Category c : pCategoryList) {
            result += c.getName();
            result += " ";
        }

        return result;
    }

    public static String distanceFromSeattleCenterInMiles(String lat, String lng) {
        Location loc1 = new Location("");
        loc1.setLatitude(Double.valueOf(lat));
        loc1.setLongitude(Double.valueOf(lng));

        Float distanceInMeters = loc1.distanceTo(SEATTLE_CENTER_LOCATION);
        Double distanceInMiles = 0.000621371 * distanceInMeters;
        return df.format(distanceInMiles);
    }
}
