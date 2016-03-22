package com.example.gauravmittal.hsdemo.places;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class Place {

    private String name;

    private String id;

    private String icon;

    @SerializedName("photos")
    private List<PlacePhoto> photos;

    private Geometry geometry;

    public Place(String name, String id, String iconUrl, Geometry geo, String photoMetadata) {
        this.name = name;
        this.id = id;
        this.icon = iconUrl;
        this.geometry = geo;
        this.photos = new ArrayList<>();
        photos.add(new PlacePhoto(photoMetadata));
    }

    public String getIconUrl() {
        return icon;
    }

    public String getPhotoUrl() {
        if (photos != null && photos.size() > 0) {
            if (!TextUtils.isEmpty(photos.get(0).getPhotoUrl()))
                return photos.get(0).getPhotoUrl();
        }
        return getIconUrl();
    }

    public String getPhotoMetadata() {
        if (photos != null && photos.size() > 0) {
            return photos.get(0).getMetadata();
        }
        return null;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<PlacePhoto> getPhotos() {
        return photos;
    }

    public static class Geometry {

        private ReverseGeocodeLocation location;

        public Geometry(ReverseGeocodeLocation loc) {
            this.location = loc;
        }

        public ReverseGeocodeLocation getLocation() {
            return location;
        }

        public void setLocation(ReverseGeocodeLocation location) {
            this.location = location;
        }
    }

    public static class ReverseGeocodeLocation {

        private Double lat;
        private Double lng;

        public ReverseGeocodeLocation(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public Double getLng() {
            return lng;
        }

    }
}
