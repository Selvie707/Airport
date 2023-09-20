package com.example.airport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private final Context context;
    public static final String DATABASE_NAME = "dbAirport";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tbAirport";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_ADDRESS = "address";
    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_NAME + " VARCHAR(60)," + FIELD_CITY +
                " VARCHAR(60)," + FIELD_ADDRESS + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long AddAirport(String name, String city, String address) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_CITY, city);
        contentValues.put(FIELD_ADDRESS, address);

        long execution = writableDatabase.insert(TABLE_NAME, null, contentValues);
        return execution;
    }

    public Cursor ReadAirportData() {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if (writableDatabase != null) {
            cursor = writableDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public long DeleteAirport(String id) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();

        long execution = writableDatabase.delete(TABLE_NAME, "id = ?", new String[]{id});
        return execution;
    }

    public long EditAirport(String id, String name, String city, String address) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_CITY, city);
        contentValues.put(FIELD_ADDRESS, address);

        long execution = writableDatabase.update(TABLE_NAME, contentValues,"id = ?", new String[]{id});
        return execution;
    }
}