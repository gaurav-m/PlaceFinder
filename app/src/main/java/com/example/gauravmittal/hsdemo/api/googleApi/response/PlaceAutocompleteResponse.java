package com.example.gauravmittal.hsdemo.api.googleApi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class PlaceAutocompleteResponse {

    @SerializedName("predictions")
    private List<GooglePrediction> googlePredictionList;

    public List<GooglePrediction> getGooglePredictionList() {
        return googlePredictionList;
    }


    public class GooglePrediction {

        private String description;

        public String getDescription() {
            return description;
        }
    }
}
