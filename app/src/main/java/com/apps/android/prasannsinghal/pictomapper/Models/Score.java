package com.apps.android.prasannsinghal.pictomapper.Models;

public class Score {
    public String ID;
    public String UserID;
    public String PlayID;
    public int score;

    public Score(String id, String ui, String pi, int distancekm){
        ID = id;
        UserID = ui;
        PlayID = pi;
        score = calculateScore(distancekm);
    }

    public Score(){
        ID = "random"+(int)Math.random()*10;
        UserID = "0";
        PlayID = "0";
        score = 0;
    }

    public int calculateScore(int in){
        int ans = 0;
        if(in<=5)
            ans = 1000;
        else if(in<=10)
            ans = 750;
        else if(in<=50)
            ans = 500;
        else if(in<=100)
            ans = 250;
        else if(in<=500)
            ans = 100;
        else if(in<=1000)
            ans = 50;
        else if(in<=5000)
            ans = 25;
        else if(in<=10000)
            ans = 10;

        return ans;



    }
}
