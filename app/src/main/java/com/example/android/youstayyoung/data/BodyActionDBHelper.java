package com.example.android.youstayyoung.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.youstayyoung.data.BodyActionContract.BodyActionEntry;

/**
 * Database helper for Stay Young app. Manages database creation and version management.
 */
public class BodyActionDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BodyActionDBHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "actions.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link BodyActionDBHelper}.
     *
     * @param context of the app
     */
    public BodyActionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the body table
        String SQL_CREATE_BODY_TABLE = "CREATE TABLE " + BodyActionEntry.TABLE_NAME + " ("
                + BodyActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BodyActionEntry.COLUMN_BODY_NAME + " TEXT NOT NULL, "
                + BodyActionEntry.COLUMN_BODY_TIME + " INTEGER, "
                + BodyActionEntry.COLUMN_BODY_DISTANCE + " INTEGER , "
                + BodyActionEntry.COLUMN_BODY_CALORIES + " INTEGER NOT NULL DEFAULT 0);";

        Log.v(LOG_TAG, SQL_CREATE_BODY_TABLE);

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BODY_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}