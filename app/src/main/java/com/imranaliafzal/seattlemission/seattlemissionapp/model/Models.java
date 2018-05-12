package com.imranaliafzal.seattlemission.seattlemissionapp.model;

import java.io.Serializable;
import java.util.List;

import lombok.Value;

/**
 * SeattleMission
 * <p>
 * Created by iafzal on 5/10/18.
 * Copyright © 2018 Imran Afzal All rights reserved.
 *
 * Immutable Data model classes
 *
 */
public class Models implements Serializable {

    @Value
    public class VenueSearchResponse implements Serializable {
        Meta meta;
        VenuesResponse response;
    }

    @Value
    public class Meta implements Serializable {
        String code;
        String requestId;

    }

    @Value
    public class Geocode implements Serializable {
        Feature feature;

    }

    @Value
    public class Feature implements Serializable {
        Geometry geometry;
        String name;
    }

    @Value
    public class Geometry implements Serializable {
        Center center;
    }

    @Value
    public class Center implements Serializable {
        String lat;
        String lng;
    }


    @Value
    public class VenuesResponse implements Serializable {
        List<Venue> venues;
        Geocode geocode;
    }


    @Value
    public class Contact implements Serializable {
        String phone;
        String formattedPhone;

    }

    @Value
    public class Location implements Serializable {
        String address;
        String crossStreet;
        String lat;
        String lng;

        List<LabeledLatLng> labeledLatLngs;

        String postalCode;
        List<String> formattedAddress;
    }

    @Value
    public class LabeledLatLng implements Serializable {
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
    public class Category implements Serializable {
        String id;
        String name;
        String pluralName;
        String shortName;
        Icon icon;
        Boolean primary;
    }

    @Value
    public class Icon implements Serializable {
        String prefix;
        String suffix;
    }

    @Value
    public class VenueChain implements Serializable {
    }

    @Value
    public class Stats implements Serializable {
        Integer tipCount, usersCount, checkinsCount, visitsCount;

    }

    @Value
    public class BeenHere implements Serializable {
        Integer count, lastCheckinExpiredAt, unconfirmedCount;
        Boolean marked;
    }

    @Value
    public class HereNow implements Serializable {
        Integer count;
        String summary;
        List<Group> groups;
    }

    @Value
    public class Group implements Serializable {
    }

    @Value
    public class Venue implements Serializable {

        String id;
        String name;
        Location location;
        Contact contact;
        Boolean verified;
        String referralId;
        Boolean hasPerk;
        List<VenueChain> venueChains;
        List<Category> categories;
        Stats stats;
        BeenHere beenHere;
        HereNow hereNow;
        String canonicalUrl;
        String url;
        Price price;
        Likes likes;
        Boolean dislike;
        Boolean ok;
        String rating;
        String ratingColor;
        Hours hours;

    }

    @Value
    public class Hours {
        String status;
        Boolean isOpen;
    }

    @Value
    public class Price implements Serializable {
        String tier;
        String message;
        String currency;
    }

    @Value
    public class Likes implements Serializable {
        String count;
        String summary;

    }

    @Value
    public class VenuePhotosResponse implements Serializable {
        Meta meta;
        PhotosResponse response;
    }

    @Value
    public class PhotosResponse {
        Photos photos;
    }

    @Value
    public class Photos {
        Integer count;
        List<PhotoItem> items;
    }

    @Value
    public class PhotoItem {
        String id;
        String prefix;
        String suffix;
    }

    @Value
    public class VenueDetailsResponse {
        Meta meta;
        SingleVenueDetailResponse response;
    }

    @Value
    public class SingleVenueDetailResponse {
        Venue venue;
    }

    @Value
    public class VenueSuggest {
        Meta meta;
        VenueSuggestResponse response;
    }

    @Value
    public class VenueSuggestResponse {
        List<Venue> minivenues;
    }

}

