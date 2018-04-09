package com.apps.android.prasannsinghal.pictomapper;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by Prasann Singhal on 4/7/2018.
 */

public class MonumentModel {
    public String name;
    public String description;
    public String imageURL;
    public double lat;
    public double lng;

    public MonumentModel(String json){
        try {
            JSONObject response = new JSONObject(json);
            this.name = response.optString("title");

            this.description = response.optString("description");

            JSONObject imgObject = response.optJSONObject("originalimage");
            this.imageURL = imgObject.optString("source");

            JSONObject locObject = response.optJSONObject("coordinates");
            this.lat = Double.parseDouble(locObject.optString("lat"));
            this.lng = Double.parseDouble(locObject.optString("lng"));
        }
        catch (Exception ex){
            Log.d("MonumentModel",ex.getMessage());
        }
    }
}
