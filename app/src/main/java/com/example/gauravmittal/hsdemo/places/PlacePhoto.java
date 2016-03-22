package com.example.gauravmittal.hsdemo.places;

import android.text.TextUtils;

import com.example.gauravmittal.hsdemo.api.googleApi.GoogleRestClientService;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class PlacePhoto {

    private int height = 0;

    private int width = 0;

    @SerializedName("photo_reference")
    private String photoRef = null;

    public PlacePhoto(String metadata)
    {
        try {
            JSONObject obj = new JSONObject(metadata);
            height = obj.getInt("height");
            width = obj.getInt("width");
            photoRef = obj.getString("photoRef");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public String getPhotoUrl() {
        if (TextUtils.isEmpty(getPhotoRef()))
            return null;

        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference="
                + getPhotoRef()
                + "&key="
                + GoogleRestClientService.PLACE_KEY;
    }

    public String getMetadata() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("height", height);
            obj.put("width", width);
            obj.put("photoRef", photoRef);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }


}
