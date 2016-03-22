package com.example.gauravmittal.hsdemo.api.googleApi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class ReverseGeocodeResponse {

    @SerializedName("results")
    private List<ReverseGeocodeData> reverseGeocodeDataList;

    public List<ReverseGeocodeData> getReverseGeocodeDataList() {
        return reverseGeocodeDataList;
    }


    public static class ReverseGeocodeData {

        private String formatted_address;

        private Geometry geometry;

        public String getFormatted_address() {
            return formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }
    }

    public static class Geometry {

        private ReverseGeocodeLocation location;

        public ReverseGeocodeLocation getLocation() {
            return location;
        }
    }

    public static class ReverseGeocodeLocation {

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
}
