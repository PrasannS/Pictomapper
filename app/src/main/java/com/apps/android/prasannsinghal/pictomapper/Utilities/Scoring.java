package com.apps.android.prasannsinghal.pictomapper.Utilities;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

public class Scoring {

    public static double getDistanceKm(LatLng StartP, LatLng EndP) {
        int Radius=6371;//radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }

    public static double evaluateScore(LatLng guess, LatLng actual){

        double distanceKm = getDistanceKm(guess, actual);
        double score = 0.;

        if (distanceKm<=35){
            score = 100.;
        } else  if (distanceKm<=100){
            score = 75.;
        }  else  if (distanceKm<=500){
            score = 50.;
        } else  if (distanceKm<=1000){
            score = 25.;
        }  else {
            score = 0.;
        }

        return score;
    }
}