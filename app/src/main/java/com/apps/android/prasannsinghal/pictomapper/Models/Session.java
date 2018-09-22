package com.apps.android.prasannsinghal.pictomapper.Models;

import com.apps.android.prasannsinghal.pictomapper.Models.Monument;

import java.util.ArrayList;
import java.util.Random;

public class Session {

    public double totalScore = 0.0;
    public int totalPlays = 0;
    public int currentplay = 0;
    public ArrayList<Play> plays = new ArrayList<>();

    public Play getCurrentPlay(){
        if(plays.size()==0){
            Random r = new Random();
            Monument m = Helper.ALL_MONUMENTS_DB.get(r.nextInt(Helper.ALL_MONUMENTS_DB.size()));
            plays.add(new Play(m));
        }
        return plays.get(currentplay);
    }

    public Session(){

    }

    public void nextPlay(){
        if(currentplay+1>=plays.size()+1) {
            Random r = new Random();
            Monument m = Helper.ALL_MONUMENTS_DB.get(r.nextInt(Helper.ALL_MONUMENTS_DB.size()));
            plays.add(new Play(m));
        }
        currentplay++;
    }

    public void previousPlay(){
        currentplay--;
    }

}
