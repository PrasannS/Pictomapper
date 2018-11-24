package com.apps.android.prasannsinghal.pictomapper.Models;

import com.apps.android.prasannsinghal.pictomapper.Models.Monument;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Play {
    public String ID;
    public String monumentID;
    public String monumentDesc;
    public LatLng monumentLatLng;
    public LatLng userGuessLatLng;
    //public double score;
    public String monumentImageURL;
    public String MonumentName;
    public long timestamp;

    public String getMonumentImageURL() {
        return monumentImageURL;
    }

    //public double getScore() {
   //     return score;
    //}

    public LatLng getMonumentLatLng() {
        return monumentLatLng;
    }

    public LatLng getUserGuessLatLng() {
        return userGuessLatLng;
    }

    public String getMonumentDesc() {
        return monumentDesc;
    }

    public String getMonumentName() {
        return MonumentName;
    }

    /*(public Play(){
        ID = "";
        MonumentName = "Something";
        monumentID = "Something";
        monumentDesc = "Something";
        monumentLatLng = new LatLng(0,0);
        monumentImageURL = "Something";
        timestamp = System.currentTimeMillis() / 1000L;
        userGuessLatLng = new LatLng(0,0);

    }*/

    public Play(Monument m){
        ID = ""+new Random().nextInt(100000);
        MonumentName = m.name;
        monumentID = m.ID;
        monumentDesc = m.description;
        monumentLatLng = new LatLng(m.lat,m.lng);
        monumentImageURL = m.imageURL;
        timestamp = System.currentTimeMillis() / 1000L;
        userGuessLatLng = new LatLng(0,0);
    }

    public void setGuess(LatLng guessLtLn){
        this.userGuessLatLng = guessLtLn;
        timestamp = System.currentTimeMillis() / 1000L;
    }
}
