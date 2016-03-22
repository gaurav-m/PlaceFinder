package com.example.gauravmittal.hsdemo.api.googleApi.response;

import com.example.gauravmittal.hsdemo.places.Place;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class PlacesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<Place> placesList;

    @SerializedName("error_message")
    private String errorMsg;

    public List<Place> getPlacesList() {
        return placesList;
    }

    public String getStatus() {
        return status;
    }
}
