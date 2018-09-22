package com.apps.android.prasannsinghal.pictomapper.Models;

import com.apps.android.prasannsinghal.pictomapper.Models.Monument;
import com.google.android.gms.maps.model.LatLng;

public class Play {
    private String monumentName;
    private String monumentDesc;
    private LatLng monumentLatLng;
    private LatLng userGuessLatLng;
    private double score;
    private String monumentImageURL;

    public String getMonumentImageURL() {
        return monumentImageURL;
    }

    public double getScore() {
        return score;
    }

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
        return monumentName;
    }

    public Play(){
        monumentLatLng = new LatLng(0.,0.);
        userGuessLatLng = new LatLng(0.,0.);
    }

    public Play(Monument m){
        monumentName = m.name;
        monumentDesc = m.description;
        monumentLatLng = new LatLng(m.lat,m.lng);
        monumentImageURL = m.imageURL;
        score = -1.0;
        userGuessLatLng = new LatLng(0.,0.);
    }

    public void setGuess(LatLng guessLtLn){
        this.userGuessLatLng = guessLtLn;
    }
}
