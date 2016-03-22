package com.example.gauravmittal.hsdemo.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gauravmittal.hsdemo.R;
import com.example.gauravmittal.hsdemo.fragments.dialog.DialogFragment;
import com.example.gauravmittal.hsdemo.location.DeviceLocation;
import com.example.gauravmittal.hsdemo.location.LocationCallbackInterface;
import com.example.gauravmittal.hsdemo.location.LocationManager;
import com.example.gauravmittal.hsdemo.views.LocationAddressView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class MapScreenFragment extends Fragment implements LocationCallbackInterface,
        MapCameraChangeListener {

    public static final int mapZoom = 15;

    LocationAddressView locationSearchView;

    String addressNotFound = "No address found";

    String serviceNotAvailable = "Service not available";

    String invalidLocation = "Invalid Location";

    String gettingAddress = "Getting Address...";

    String locationSettingEnableMsg = "Location Setting is disabled. Please enable device location setting.";

    String settings = "SETTINGS";

    public static final String TAG = MapScreenFragment.class.getName();

    private Context mContext;
    private GoogleMap mMapView;
    private SupportMapFragment map;
    private DeviceLocation selectedLocation;
    private MapController mapController;
    private HashMap<String, Integer> markerList = new HashMap<>();
    private View coordinatorLayoutView;
    private ProgressDialog mProgressDialog;

    public static final int PICKUP_SELECTED = 0;

    public static MapScreenFragment newInstance() {
        MapScreenFragment fragment = new MapScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.layout_map_fragment, container, false);

        locationSearchView = (LocationAddressView) rootView.findViewById(R.id.pickup_search_view);

        mContext = getActivity().getApplicationContext();
        locationSearchView.setLocation(gettingAddress);
        LocationManager locationManager = new LocationManager(mContext);
        locationManager.init(this);
        setupMap();
        mapController = new MapController(mContext, this);
        if (mMapView != null) {
            mMapView.setOnCameraChangeListener(mapController);
        }
        coordinatorLayoutView = rootView.findViewById(R.id.snackbar);

        locationSearchView.setOnClickListener(clickListener);
        locationSearchView.setTag(PICKUP_SELECTED);
        locationSearchView.setSearchTitle("SELECTED LOCATION");

        return rootView;
    }

    private void setupMap() {
        FragmentManager fm = getChildFragmentManager();
        map = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (map != null) {
            mMapView = map.getMap();
        }
        if (mMapView != null) {
            mMapView.getUiSettings().setZoomControlsEnabled(false);
        }
    }

    private void showMapLocation(double pickupLatitude, double pickupLongitude) {

        try {
            LatLng location = new LatLng(pickupLatitude, pickupLongitude);
            CameraUpdate center = CameraUpdateFactory.newLatLngZoom(location,
                    (float) mapZoom);

            if (mMapView != null) {
                mMapView.moveCamera(center);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private final void addDriverLocationsToMap(final ArrayList<User> drivers) {
        int i = 0;
        for (User driver : drivers) {
            LatLng l = new LatLng(Double.valueOf(driver.getLocation().getLatitude()),
                    Double.valueOf(driver.getLocation().getLongitude()));

            MarkerOptions marker = new MarkerOptions()
                    .position(l)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));

            Marker markerObj = mMapView.addMarker(marker);
            markerList.put(markerObj.getId(), i);
            i++;
        }
    }*/

    @Override
    public void onLocationChanged(DeviceLocation location) {
        if (getActivity() == null) {
            return;
        }
        showMapLocation(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onLocationSettingsChange() {
        if (getActivity() == null) {
            return;
        }
        DialogFragment dialog = DialogFragment.newInstance(locationSettingEnableMsg, settings);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        dialog.show(ft, TAG);
    }

    @Override
    public void onLocationAddressUpdate(DeviceLocation location) {
        if (getActivity() == null) {
            return;
        }
        updateCurrentLocation(location);
        locationSearchView.setLocation(location.getLocationAddress());
    }

    private void updateCurrentLocation(DeviceLocation location) {
        selectedLocation = location;
    }

    @Override
    public void onCameraChange() {
        locationSearchView.setLocation(gettingAddress);
    }

    @Override
    public void onAddressNotFound() {
        locationSearchView.setLocation(addressNotFound);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (Integer) v.getTag();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @Subscribe
//    public void onLocationSelection(RiderLocationChangeEvent event) {
//        if(getActivity() == null) {
//            return;
//        }
//        if(event.selection == PICKUP_SELECTED) {
//            mMapView.setOnCameraChangeListener(null);
//            updateCurrentLocation(event.location);
//            showMapLocation(event.location.getLatitude(), event.location.getLongitude());
//            locationSearchView.setLocation(event.location.getLocationAddress());
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mMapView.setOnCameraChangeListener(mapController);
//                }
//            }, 1500);
//        } else {
//            dropSearchView.setLocation(event.location.getLocationAddress());
//            updateDropLocation(event.location);
//        }
//    }
//
//    @Subscribe
//    public void openLocationSetting(DropDialogButtonClickEvent event) {
//        if(getActivity() == null) {
//            return;
//        }
//        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivityForResult(callGPSSettingIntent, 10);
//    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public DeviceLocation getSelectedLocation() {
        return selectedLocation;
    }
}
