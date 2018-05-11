package com.imranaliafzal.seattlemission.seattlemissionapp.model;

import java.io.Serializable;
import java.util.List;

import lombok.Value;

/**
 * HomeAwayFromHome
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Spendlabs Inc. All rights reserved.
 */
public class Models implements Serializable{

    @Value
    public class VenueSearchResponse implements Serializable{
        Meta meta;
        VenuesResponse response;
    }

    @Value
    public class Meta implements Serializable{
        String code;
        String requestId;

    }

    @Value
    public class Geocode implements Serializable{
        Feature feature;

    }

    @Value
    public class Feature implements Serializable{
        Geometry geometry;
        String name;
    }

    @Value
    public class Geometry implements Serializable{
        Center center;
    }

    @Value
    public class Center implements Serializable{
        String lat;
        String lng;
    }



    @Value
    public class VenuesResponse implements Serializable{
        List<Venue> venues;
        Geocode geocode;
    }


    @Value
    public class Contact implements Serializable{

    }

    @Value
    public class Location implements Serializable{
        String address;
        String crossStreet;
        String lat;
        String lng;

        List<LabeledLatLng> labeledLatLngs;

        String postalCode;
    }

    @Value
    public class LabeledLatLng implements Serializable{
        String label;
        String lat;
        String lng;
        String cc;
        String city;
        String state;
        String country;
        List<String> formattedAddress;
    }

    @Value
    public class Category implements Serializable{
        String id;
        String name;
        String pluralName;
        String shortName;
        Icon icon;
        Boolean primary;
    }
    @Value
    public class Icon implements Serializable{
        String prefix;
        String suffix;
    }

    @Value
    public class VenueChain implements Serializable{
    }

    @Value
    public class Stats implements Serializable{
        Integer tipCount, usersCount,checkinsCount,visitsCount;

    }

    @Value
    public class BeenHere implements Serializable{
        Integer count,lastCheckinExpiredAt,unconfirmedCount;
        Boolean marked;
    }

    @Value
    public class HereNow implements Serializable{
        Integer count;
        String summary;
        List<Group> groups;
    }

    @Value
    public class Group implements Serializable{
    }

    @Value
    public class Venue implements Serializable{

        String  id;
        String  name;
        Location location;
        Contact contact;
        Boolean verified;
        String referralId;
        Boolean hasPark;
        List<VenueChain> venueChains;
        List<Category> categories;
        Stats stats;
        BeenHere beenHere;
        HereNow hereNow;

    }

    @Value
    public class VenuePhotosResponse implements Serializable{
        Meta meta;
        PhotosResponse response;
    }

    @Value
    public class PhotosResponse{
        Photos photos;
    }

    @Value
    public class Photos{
        Integer count;
        List<PhotoItem> items;
    }

    @Value
    public class PhotoItem{
        String id;
        String prefix;
        String suffix;
    }

}

