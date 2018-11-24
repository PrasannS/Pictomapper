package com.apps.android.prasannsinghal.pictomapper.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.android.prasannsinghal.pictomapper.Models.*;
import com.google.android.gms.maps.model.LatLng;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

import static com.apps.android.prasannsinghal.pictomapper.persistence.PictomapperDBHelper.MONUMENTS_TABLE_NAME;
import static com.apps.android.prasannsinghal.pictomapper.persistence.PictomapperDBHelper.PLAYS_TABLE_NAME;

public class PictomapperDAO {

    private SQLiteDatabase database;
    private PictomapperDBHelper dbHelper;
    private SharedPreferences loginPreferences;
    private String[] allColumns = {
            "ID",
            "Description",
            "Name",
            "Latitude",
            "Longitude",
            "ImgURL",
            "DetailedDescription"
    };

    private String[] allPlayColumns = {
            "ID",
            "MonumentID",
            "MonumentLatitude",
            "MonumentLongitude",
            "GuessLatitude",
            "GuessLongitude",
            "DateTimeStamp",
            "Status"
    };

    public String[] allUserColumns = {
            "ID",
            "UserName",
            "SessionsPlayed",
            "Age",
            "DateTimeStamp"
    };

    public String[] allScoreColumns = {
            "ID",
            "UserID",
            "PlayID",
            "Score",
            "DateTimeStamp"
    };


    public PictomapperDAO() {

    }

    public PictomapperDAO(Context context) {
        dbHelper = new PictomapperDBHelper(context);
    }

    public void open() throws SQLException {
        database=dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addMonument(Monument m){

        //the following code


        ContentValues values = new ContentValues();
        values.put("ID",m.ID);
        values.put("Description",m.description);
        values.put("Name",m.name);
        values.put("Latitude",m.lat);
        values.put("Longitude",m.lng);
        values.put("ImgURL",m.imageURL);
        values.put("DetailedDescription",m.detailedDescription);

        long insertID = database.insert(PictomapperDBHelper.MONUMENTS_TABLE_NAME, null, values);
        /*Cursor cursor = database.query(PictomapperDBHelper.MONUMENTS_TABLE_NAME, allColumns, "ID" + " = " + "\""+ r+"\"", null, null, null, null );
        cursor.moveToFirst();
        Monument newM = cursorToMonument(cursor);
        cursor.close();*/


    }

    public void addPlay(Play p){

        ContentValues values = new ContentValues();
        Date date = new Date();

        values.put("ID",p.ID);
        values.put("MonumentID",p.monumentID);
        values.put("MonumentLatitude",p.monumentLatLng.latitude);
        values.put("MonumentLongitude",p.monumentLatLng.longitude);
        values.put("GuessLatitude",p.userGuessLatLng.latitude);
        values.put("GuessLongitude",p.userGuessLatLng.longitude);
        values.put("Timestamp",p.timestamp);
        //values.put("DateTimeStamp",new Timestamp(date.getTime()).toString());

        long insertID = database.insert(PictomapperDBHelper.PLAYS_TABLE_NAME,null, values);
        Log.d("addPlay",insertID+"");
    }

    /*public void setStatus(int i){
        ContentValues values = new ContentValues();
        values.put("Status",i);
        //database.update(PictomapperDBHelper.PLAYS_TABLE_NAME,values,"_id="+getColCount("PLAYS_TABLE_NAME"), null);
    }*/

    public void addUser(User user){
        ContentValues values = new ContentValues();
        Date date = new Date();

        values.put("ID",user.ID);
        values.put("SessionsPlayed",user.SessionsPlayed);
        values.put("UserName",user.UserName);
        values.put("Age",user.age);
        values.put("DateTimeStamp",new Timestamp(date.getTime()).toString());

        long insertID = database.insert(PictomapperDBHelper.USERS_TABLE_NAME,null, values);
        Log.d("addUser",insertID+"");



    }

    public void addScore(Score s){
        ContentValues values = new ContentValues();
        Date date = new Date();

        values.put("ID",s.ID);
        values.put("UserID",s.UserID);
        values.put("PlayID",s.PlayID);
        values.put("Score",s.score);
        values.put("DateTimeStamp",new Timestamp(date.getTime()).toString());

        long insertID = database.insert(PictomapperDBHelper.SCORES_TABLE_NAME,null, values);


    }

    public Monument getMonument(String id){
        Cursor cursor = database.query(MONUMENTS_TABLE_NAME, allColumns, "ID" + " = " + "\""+ id+"\"", null, null, null, null );
        cursor.moveToFirst();
        Monument newM = cursorToMonument(cursor);
        cursor.close();
        return newM;
    }

    public Play getPlay(String id){
        Cursor cursor = database.query(PictomapperDBHelper.PLAYS_TABLE_NAME, allPlayColumns, "ID" + " = " + "\""+ id +"\"", null, null, null, null);
        cursor.moveToFirst();
        Play p = cursorToPlay(cursor);
        cursor.close();
        return p;
    }

    public User getUser(String id){
        Cursor cursor = database.query(PictomapperDBHelper.USERS_TABLE_NAME, allUserColumns, "ID" + " = " + "\""+ id +"\"", null, null, null, null);
        cursor.moveToFirst();
        User p = cursorToUser(cursor);
        cursor.close();
        return p;
    }

    public int getScore(){
        Cursor cursor = database.query(PictomapperDBHelper.SCORES_TABLE_NAME, allScoreColumns, null, null, null, null, null);
        cursor.moveToFirst();
        int totalScore = 0;
        while(cursor.isAfterLast()==false) {
            Score p = cursorToScore(cursor);
            cursor.moveToNext();
            totalScore+=p.score;

        }
        cursor.close();
        return totalScore;
    }

    public ArrayList<Monument> getAllMonuments(){

        Cursor  cursor = database.rawQuery("SELECT * FROM "+ MONUMENTS_TABLE_NAME,null);

        ArrayList<Monument> mons = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Monument newM = cursorToMonument(cursor);
                mons.add(newM);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mons;
    }

    private Play cursorToPlay(Cursor cursor){
        Monument mo = new Monument();
        Play m = new Play(mo);
        m.ID  = cursor.getString(cursor.getColumnIndex("ID"));
        m.monumentImageURL = getMonument(m.ID).imageURL;
        m.monumentDesc = getMonument(m.ID).description;
        //m.status = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Status")));
        m.monumentID= cursor.getString(cursor.getColumnIndex("MonumentID"));
        m.monumentLatLng = new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex("MonumentLatitude"))),Double.parseDouble(cursor.getString(cursor.getColumnIndex("MonumentLongitude"))));
        m.userGuessLatLng = new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex("GuessLatitude"))),Double.parseDouble(cursor.getString(cursor.getColumnIndex("GuessLongitude"))));
        m.timestamp = cursor.getLong(cursor.getColumnIndex("Timestamp"));
        return m;
    }

    private Monument cursorToMonument(Cursor cursor) {
        Monument m = new Monument();
        m.ID  = cursor.getString(cursor.getColumnIndex("ID"));
        m.description = cursor.getString(cursor.getColumnIndex("Description"));
        m.name= cursor.getString(cursor.getColumnIndex("Name"));
        m.lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Latitude")));
        m.lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Longitude")));
        m.imageURL = cursor.getString(cursor.getColumnIndex("ImgURL"));
        m.detailedDescription = cursor.getString(cursor.getColumnIndex("DetailedDescription"));

        return m;
    }

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.ID  = cursor.getString(cursor.getColumnIndex("ID"));
        user.UserName = cursor.getString(cursor.getColumnIndex("UserName"));
        user.SessionsPlayed = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SessionsPlayed")));
        user.age = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Age")));

        return user;

    }

    private Score cursorToScore(Cursor cursor){
        Score score = new Score();
        score.ID = cursor.getString(cursor.getColumnIndex("ID"));
        score.PlayID = cursor.getString(cursor.getColumnIndex("PlayID"));
        score.score = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Score")));
        score.UserID = cursor.getString(cursor.getColumnIndex("UserID"));
        return score;
    }

    public int getColCount(String name) {
        Cursor cursor = database.query(name, allColumns,null, null, null, null, null);
        cursor.moveToLast();
        int ans = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
        return ans;
        //long count = DatabaseUtils.queryNumEntries(database, name);
        //return (int)count;
    }

    /*public Play getCurrentPlay(){
        Play p = null;
        if(getColCount("PLAYS_TABLE_NAME")==0){
            return p;
        }
        int i = getPlay(getColCount("PLAYS_TABLE_NAME")+"").status;
        if(i==0){
            return p;
        }
        else if(i>0){
            int id = (getColCount("PLAYS_TABLE_NAME")- i);
            return getPlay(""+id);
        }
        return p;
    }*/

}
