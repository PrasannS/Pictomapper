package com.apps.android.prasannsinghal.pictomapper.Models;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by Prasann Singhal on 4/7/2018.
 */

public class Monument {
    public String ID;
    public String name;
    public String description;
    public String imageURL;
    public double lat;
    public double lng;
    public String detailedDescription;

    public Monument() {

    }

    public Monument(String json){
        try {
            JSONObject response = new JSONObject(json);
            this.name = response.optString("title");

            this.description = response.optString("description");

            JSONObject imgObject = response.optJSONObject("originalimage");
            this.imageURL = imgObject.optString("source");

            JSONObject locObject = response.optJSONObject("coordinates");
            this.lat = Double.parseDouble(locObject.optString("lat"));
            this.lng = Double.parseDouble(locObject.optString("lon"));

            this.detailedDescription = response.optString("extract");
        }
        catch (Exception ex){
            Log.d("Monument",ex.getMessage());
        }
    }

    public static Monument[] fromCSV(String[] stats){
        //"Great Wall of China
        // %#%series of fortifications built along the historical border of China
        // %#%40.68
        // %#%117.23
        // %#%https://upload.wikimedia.org/wikipedia/commons/2/23/The_Great_Wall_of_China_at_Jinshanling-edit.jpg
        // %#%The Great Wall of China is a series of fortifications made of stone, brick, tamped earth, wood, and other materials, generally built along an east-to-west line across the historical northern borders of China to protect the Chinese states and empires against the raids and invasions of the various nomadic groups of the Eurasian Steppe. Several walls were being built as early as the 7th century BC; these, later joined together and made bigger and stronger, are collectively referred to as the Great Wall. Especially famous is the wall built in 220–206 BC by Qin Shi Huang, the first Emperor of China. Little of that wall remains. The Great Wall has been rebuilt, maintained, and enhanced over various dynasties; the majority of the existing wall is from the Ming Dynasty (1368–1644)."
        Monument [] ms = new Monument[stats.length];
        String[] strings = new String[6];
        for(int i = 0; i<stats.length;i++){
            strings = stats[i].split("%#%");
            Monument m = new Monument();

            if (strings!=null&&strings.length>=6){
                m.name = strings[0];
                m.description = strings[1];
                m.lat = Double.parseDouble(strings[2]);
                m.lng = Double.parseDouble(strings[3]);
                m.imageURL = strings[4];
                m.detailedDescription = strings[5];
                m.ID = ((Integer)i).toString();

            }
            ms[i]=m;
        }

        return ms;
    }
    public static Monument fromString(String stats){
        //"Great Wall of China
        // %#%series of fortifications built along the historical border of China
        // %#%40.68
        // %#%117.23
        // %#%https://upload.wikimedia.org/wikipedia/commons/2/23/The_Great_Wall_of_China_at_Jinshanling-edit.jpg
        // %#%The Great Wall of China is a series of fortifications made of stone, brick, tamped earth, wood, and other materials, generally built along an east-to-west line across the historical northern borders of China to protect the Chinese states and empires against the raids and invasions of the various nomadic groups of the Eurasian Steppe. Several walls were being built as early as the 7th century BC; these, later joined together and made bigger and stronger, are collectively referred to as the Great Wall. Especially famous is the wall built in 220–206 BC by Qin Shi Huang, the first Emperor of China. Little of that wall remains. The Great Wall has been rebuilt, maintained, and enhanced over various dynasties; the majority of the existing wall is from the Ming Dynasty (1368–1644)."
        String[] strings = new String[6];

            strings = stats.split("%#%");
            Monument m = new Monument();

            if (strings!=null&&strings.length>=6){
                m.name = strings[0];
                m.description = strings[1];
                m.lat = Double.parseDouble(strings[2]);
                m.lng = Double.parseDouble(strings[3]);
                m.imageURL = strings[4];
                m.detailedDescription = strings[5];

            }

        return m;
    }

    public String toString(){
        String s = String.format("%s,%s,%.3f,%.3f,%s,%s", this.ID, this.description, this.lat, this.lng, this.imageURL, this.detailedDescription);
        return s;
    }
}