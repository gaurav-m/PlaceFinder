package com.example.gauravmittal.hsdemo.api.googleApi;


import com.example.gauravmittal.hsdemo.api.googleApi.response.GeocodeResponse;
import com.example.gauravmittal.hsdemo.api.googleApi.response.PlacesResponse;
import com.example.gauravmittal.hsdemo.api.googleApi.response.ReverseGeocodeResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by gauravmittal on 20/03/16.
 */
public interface GoogleRestClientService {

    public static final String PLACE_KEY = "AIzaSyDU_fVFv2VKajfsDnnigtQYP1DCFY9XTkE";

    String GEOCODE_URL = "/maps/api/geocode/json";

    String PLACES_URL = "/maps/api/place/nearbysearch/json";

    @GET(GEOCODE_URL)
    public void getLocationAddress(
            @Query("latlng") String location,
            Callback<GeocodeResponse> callback);

    @GET(GEOCODE_URL)
    public void getAddressFromLocation(
            @Query("address") String address,
            Callback<ReverseGeocodeResponse> callback);

    @GET(PLACES_URL)
    public void getPlacesFromLocation(
            @Query("location") String location,
            @Query("radius") String radius,
            @Query("types") String type,
            @Query("key") String key,
            Callback<PlacesResponse> callback);
}
