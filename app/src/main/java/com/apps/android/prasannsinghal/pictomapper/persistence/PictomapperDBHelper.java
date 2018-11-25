package com.apps.android.prasannsinghal.pictomapper.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.apps.android.prasannsinghal.pictomapper.Models.Monument;

import static com.apps.android.prasannsinghal.pictomapper.MONUMENTS.ALL_MONUMENTS;


public class PictomapperDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "DB_PICTOMAPPER";
    private static final int DATABASE_VERSION = 2;
    public static final String MONUMENTS_TABLE_NAME = "TBL_MONUMENTS";
    public static final String PLAYS_TABLE_NAME = "TBL_PLAYS";
    public static final String USERS_TABLE_NAME = "TBL_USERS";
    public static final String SCORES_TABLE_NAME ="TBL_SCORES";
    private SQLiteDatabase database;

    private static final String MONUMENTS_TABLE_CREATE =
            "CREATE TABLE " + MONUMENTS_TABLE_NAME + " (" +
                    "ID" + " TEXT primary key, " +
                    "Name" + " TEXT, " +
                    "Latitude" + " REAL, " +
                    "Longitude" + " REAL, " +
                    "Description" + " TEXT, " +
                    "DetailedDescription" + " TEXT, " +
                    "ImgURL" + " TEXT);";

    private static final String PLAYS_TABLE_CREATE =
            "CREATE TABLE " + PLAYS_TABLE_NAME + " (" +
                    "ID" + " TEXT primary key, " +
                    "MonumentID" + " TEXT, " +
                    "MonumentLatitude" + " REAL, " +
                    "MonumentLongitude" + " REAL, " +
                    "GuessLatitude" + " REAL, " +
                    "GuessLongitude" + " REAL, " +
                    "TimeStamp" + " BIGINT, " +
                    " FOREIGN KEY (MonumentID) REFERENCES "+MONUMENTS_TABLE_NAME+"(ID));";

    private static final String USERS_TABLE_CREATE =
            "CREATE TABLE " + USERS_TABLE_NAME + " (" +
                    "ID" + " TEXT primary key, " +
                    "UserName" + " TEXT, " +
                    "SessionsPlayed" + " REAL, " +
                    "Age" + " REAL, " +
                    "DateTimeStamp" + " TEXT);";

    private static final String SCORES_TABLE_CREATE =
            "CREATE TABLE " + SCORES_TABLE_NAME + " (" +
                    "ID" + " TEXT primary key, " +
                    "UserID" + " TEXT, " +
                    "PlayID" + " TEXT, " +
                    "Score" + " REAL, " +
                    "DateTimeStamp" + " TEXT, " +
                    "FOREIGN KEY (UserID) REFERENCES "+USERS_TABLE_NAME+"(ID), "+
                    "FOREIGN KEY (PlayID) REFERENCES "+PLAYS_TABLE_NAME+"(ID));";






    PictomapperDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create tables below
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(MONUMENTS_TABLE_CREATE);
        db.execSQL(PLAYS_TABLE_CREATE);
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(SCORES_TABLE_CREATE);
        database = this.getWritableDatabase();
        saveInDatabase();
        // logic for data loading
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + MONUMENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCORES_TABLE_NAME);
        onCreate(db);
    }

    public void saveInDatabase(){
        Monument[] monuments = Monument.fromCSV(ALL_MONUMENTS);
        for(Monument m:monuments){
            try{
                addMonument(m);
            }
            catch (Exception ex){
                Log.e("saveInDatabase",ex.getMessage());
            }

        }
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






}
