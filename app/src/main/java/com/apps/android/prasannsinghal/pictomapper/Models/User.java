package com.apps.android.prasannsinghal.pictomapper.Models;

public class User {
    public String ID;
    public String UserName;
    public int SessionsPlayed;
    public int age;

    public User(String id, String un, int sp, int a){
        ID = id;
        UserName = un;
        SessionsPlayed = sp;
        age = a;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "ID - "+ID+", Name - "+UserName+", Sessions Played - "+SessionsPlayed+", Age - "+age;
    }
}
