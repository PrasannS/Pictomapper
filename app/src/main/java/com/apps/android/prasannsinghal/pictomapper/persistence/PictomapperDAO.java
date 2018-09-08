package com.apps.android.prasannsinghal.pictomapper.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

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

    public PictomapperDAO(Context context) {
        dbHelper = new PictomapperDBHelper(context);
    }

    //Upload Pictomapper


}
