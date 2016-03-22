package com.example.gauravmittal.hsdemo.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.gauravmittal.hsdemo.api.googleApi.GoogleRestClient;
import com.example.gauravmittal.hsdemo.api.googleApi.response.GeocodeResponse;
import com.example.gauravmittal.hsdemo.location.DeviceLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gauravmittal on 03/10/15.
 */
public class MapController implements GoogleMap.OnCameraChangeListener{

    private Context mContext;
    private MapCameraChangeListener listener;
    private static final int MESSAGE_UPDATE = 1001;
    private static final int GEOCODER_RETRY = 5;
    private static int geocoderRetryCount;
    private GeocoderAddressAsync mGeocoderAddressAsync;

    public MapController(Context context, MapCameraChangeListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        double latitude = cameraPosition.target.latitude;
        double longitude = cameraPosition.target.longitude;
        if(latitude > 0 && longitude > 0) {
            requestLocationAddress(latitude, longitude);
        }
    }

    private void getLocationAddress(final DeviceLocation location) {
        if(Geocoder.isPresent() && (geocoderRetryCount < GEOCODER_RETRY)) {
            mGeocoderAddressAsync = new GeocoderAddressAsync(location);
            mGeocoderAddressAsync.execute();
            return;
        }
        GoogleRestClient client = GoogleRestClient.with(mContext);
        listener.onCameraChange();
        client.getLocationAddress(location.getLatitude(), location.getLongitude(), new Callback<GeocodeResponse>() {
            @Override
            public void success(GeocodeResponse geocodeResponse, Response response) {
                StringBuilder locationAddress = new StringBuilder();
                if(geocodeResponse == null || geocodeResponse.getAdresssList().size() == 0) {
                    listener.onAddressNotFound();
                    return;
                }
                List<GeocodeResponse.AddressComponent> addresses = geocodeResponse.getAdresssList().
                        get(0).getAddressComponents();
                for(GeocodeResponse.AddressComponent address : addresses) {
                    locationAddress.append(address.getComponentAddress() + " ");
                }
                geocoderRetryCount = 0;
                location.setLocationAddress(locationAddress.toString());
                listener.onLocationAddressUpdate(location);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestLocationAddress(double lat, double lng) {
        DeviceLocation deviceLocation = new DeviceLocation(lat, lng);
        mHandler.removeMessages(MESSAGE_UPDATE);
        mHandler.sendMessageDelayed(Message.obtain(mHandler, MESSAGE_UPDATE,
                deviceLocation), 200);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_UPDATE) {
                DeviceLocation location = (DeviceLocation) msg.obj;
                getLocationAddress(location);
            }
        }
    };


    private String getAddressUsingGeocoder(double lat, double lng) {
        List<Address> addresses = null;
        StringBuilder locationAddress = new StringBuilder();
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException ioException) {
            geocoderRetryCount++;
        } catch (IllegalArgumentException illegalArgumentException) {
            geocoderRetryCount++;
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            geocoderRetryCount++;
            return "";
        } else {
            Address address = addresses.get(0);

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                locationAddress.append(address.getAddressLine(i) + " ");
                locationAddress.toString();
            }
            return locationAddress.toString();
        }
    }

    class GeocoderAddressAsync extends AsyncTask<Void, Void, String> {

        DeviceLocation location;

        public GeocoderAddressAsync(DeviceLocation location) {
            this.location = location;
        }

        @Override
        protected String doInBackground(Void... params) {
            String address = getAddressUsingGeocoder(location.getLatitude(), location.getLongitude());
            return address;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.length() > 0) {
                geocoderRetryCount = 0;
                DeviceLocation deviceLocation = new DeviceLocation(location.getLatitude(),
                        location.getLongitude(), result);
                listener.onLocationAddressUpdate(deviceLocation);
            } else {
                if (geocoderRetryCount == GEOCODER_RETRY) {
                    getLocationAddress(location);
                } else {
                    mGeocoderAddressAsync = new GeocoderAddressAsync(location);
                    mGeocoderAddressAsync.execute();
                }
            }
        }
    }
}
