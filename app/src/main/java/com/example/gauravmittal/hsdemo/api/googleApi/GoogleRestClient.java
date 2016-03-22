package com.example.gauravmittal.hsdemo.api.googleApi;

import android.content.Context;

import com.example.gauravmittal.hsdemo.api.googleApi.response.GeocodeResponse;
import com.example.gauravmittal.hsdemo.api.googleApi.response.PlacesResponse;
import com.example.gauravmittal.hsdemo.api.googleApi.response.ReverseGeocodeResponse;
import com.example.gauravmittal.hsdemo.places.PlacesRequirement;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class GoogleRestClient {

    private static final String BASE_URL = "https://maps.googleapis.com";
    private Context context;
    private static GoogleRestClient _instance;

    private GoogleRestClientService googleRestClientService;


    public GoogleRestClient(Context context) {
        this.context = context;

        setupGoogleRestClient();
    }

    public static GoogleRestClient with(Context context) {
        if (_instance == null) {
            synchronized (GoogleRestClient.class) {
                if (_instance == null) {
                    _instance = new GoogleRestClient(context);
                }
            }
        }
        return _instance;
    }

    private void setupGoogleRestClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson));

        RestAdapter restAdapter = builder.build();
        googleRestClientService = restAdapter.create(GoogleRestClientService.class);
    }


    public void getLocationAddress(double latitude, double longitude, Callback<GeocodeResponse> responseListener) {
        String latlng = latitude + "," + longitude;
        googleRestClientService.getLocationAddress(latlng, responseListener);
    }

    public void getAddressFromLocation(String address, Callback<ReverseGeocodeResponse> responseListener) {
        googleRestClientService.getAddressFromLocation(address, responseListener);
    }

    public void getPlacesFromLocation(PlacesRequirement body, Callback<PlacesResponse> callback)
    {
        String latlng = body.getLocation().getLatitude() + "," + body.getLocation().getLongitude();
        String radius = "" + body.getRadius();
        String type = body.getType().toString();
        googleRestClientService.getPlacesFromLocation(latlng, radius, type, GoogleRestClientService.PLACE_KEY, callback);
    }

}
