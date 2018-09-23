package com.apps.android.prasannsinghal.pictomapper.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class PictomapperDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "DB_PICTOMAPPER";
    private static final int DATABASE_VERSION = 1;
    public static final String MONUMENTS_TABLE_NAME = "TBL_MONUMENTS";
    public static final String PLAYS_TABLE_NAME = "TBL_PLAYS";
    public static final String USERS_TABLE_NAME = "TBL_USERS";
    public static final String SCORES_TABLE_NAME ="TBL_SCORES";

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
                    "DateTimeStamp" + " TEXT, " +
                    "Status" + " REAL, " +
                    " FOREIGN KEY (MonumentID) REFERENCES "+MONUMENTS_TABLE_NAME+"(MonumentID));";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + MONUMENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCORES_TABLE_NAME);
        onCreate(db);
    }






}
