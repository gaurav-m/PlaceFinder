package com.example.gauravmittal.hsdemo.map;


import com.example.gauravmittal.hsdemo.location.DeviceLocation;

/**
 * Created by gauravmittal on 20/03/16.
 */
public interface MapCameraChangeListener {
    void onLocationAddressUpdate(DeviceLocation location);
    void onCameraChange();
    void onAddressNotFound();
}
