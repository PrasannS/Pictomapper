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
            ""
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
        Random r = new Random;

        ContentValues values = new ContentValues();
        values.put("ID",m.ID);
        values.put("Description",m.description);
        values.put("Name",m.name);
        values.put("Latitude",m.lat);
        values.put("Longitude",m.lng);
        values.put("ImgURL",m.imageURL);
        values.put("DetailedDescription",m.detailedDescription);

        long insertID = database.insert(PictomapperDBHelper.MONUMENTS_TABLE_NAME, null, values);
        Cursor cursor = database.query(PictomapperDBHelper.MONUMENTS_TABLE_NAME, allColumns, "ID" + " = " + "\""+ r+"\"", null, null, null, null );
        cursor.moveToFirst();
        Monument newM = cursorToMonument(cursor);
        cursor.close();

        newM.RequiredTasks = new ArrayList<TaskModel>();


    }

    private Monument cursorToMonument(Cursor cursor) {
        Monument m = new Monument();
        m.ID  = cursor.getString(0);
        m.description = cursor.getString(1);
        m.detailedDescription = cursor.getString(2);
        m.imageURL = cursor.getString(3);
        m.lat = Integer.parseInt(cursor.getString(4));
        m.lng = Integer.parseInt(cursor.getString(5));
        m.name= cursor.getString(6);

        return m;
    }
}
