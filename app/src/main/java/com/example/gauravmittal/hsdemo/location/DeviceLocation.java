package com.example.gauravmittal.hsdemo.location;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class DeviceLocation implements Parcelable{

    private double latitude;

    private double longitude;

    private String locationAddress;

    public DeviceLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DeviceLocation(double latitude, double longitude, String locationAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationAddress = locationAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public DeviceLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        locationAddress = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(locationAddress);
    }

    public static Creator<DeviceLocation> CREATOR = new Creator<DeviceLocation>() {
        @Override
        public DeviceLocation createFromParcel(Parcel source) {
            return new DeviceLocation(source);
        }

        @Override
        public DeviceLocation[] newArray(int size) {
            return new DeviceLocation[size];
        }
    };

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
