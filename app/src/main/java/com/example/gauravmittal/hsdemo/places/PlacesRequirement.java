package com.example.gauravmittal.hsdemo.places;

import com.example.gauravmittal.hsdemo.location.DeviceLocation;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class PlacesRequirement {

    @SerializedName("type")
    private PlaceType type;

    @SerializedName("radius")
    private int radius;

    @SerializedName("location")
    private DeviceLocation location;

    public PlacesRequirement(PlaceType type, int radius, DeviceLocation loc)
    {
        this.type = type;
        this.radius = radius;
        this.location = loc;
    }

    public int getRadius() {
        return radius;
    }

    public PlaceType getType() {
        return type;
    }

    public DeviceLocation getLocation() {
        return location;
    }
}
