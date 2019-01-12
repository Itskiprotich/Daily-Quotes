package com.africa3d.keeprawteach.quotes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database  extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Status.db";
    // User table name
    private static final String FAVO_USER = "favourites";
    // User Table Columns names
    private static final String FAVO_ID = "favourite_id";
    private static final String FAVO_NAME = "favourite_name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FAVO_USER + "(" +
                "favourite_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "favourite_name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FAVO_USER);

        onCreate(db);
    }

    public boolean addFavor(String share) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FAVO_NAME, share);

        long bool = db.insert(FAVO_USER, null, values);

        if (bool == -1) {

            return false;

        }

        return true;
    }

    public Cursor search() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + FAVO_USER, null);

        return cursor;
    }
}
