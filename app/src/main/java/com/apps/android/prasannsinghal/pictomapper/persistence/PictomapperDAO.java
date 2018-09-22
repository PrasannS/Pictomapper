package com.apps.android.prasannsinghal.pictomapper.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.apps.android.prasannsinghal.pictomapper.Models.*;

import java.util.ArrayList;
import java.util.Random;

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

    private String[] allColumnsTask = {

    };

    private  String[] allColumnsInventory = {

    };

    private String[] allColumnsParts = {

    };

    private String[] allColumnsPartsSerialNumbers = {

    };

    private String [] allColumnsSecondaryPictomapperComments = {

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

    public Monument getMonument(String id){
        Cursor cursor = database.query(PictomapperDBHelper.MONUMENTS_TABLE_NAME, allColumns, "ID" + " = " + "\""+ id+"\"", null, null, null, null );
        cursor.moveToFirst();
        Monument newM = cursorToMonument(cursor);
        cursor.close();
        return newM;
    }

    public ArrayList<Monument> getAllMonuments(){

        Cursor  cursor = database.rawQuery("SELECT * FROM "+PictomapperDBHelper.MONUMENTS_TABLE_NAME,null);

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
}
