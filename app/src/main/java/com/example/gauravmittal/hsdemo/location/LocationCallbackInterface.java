package com.example.gauravmittal.hsdemo.location;

/**
 * Created by gauravmittal on 20/03/16.
 */
public interface LocationCallbackInterface {
    void onLocationChanged(DeviceLocation location);
    void onLocationSettingsChange();
}
