package com.example.android.youstayyoung;
/**
 * Copyright (C) 2017 The Android Open Source Project
 * This app "You Stay Young" is for people who want to check
 * if their habits are healthy for body and soul
 * Is created with android studio 2.3.1
 * as exercise for Android Basics by Google Nanodegree Program
 * "Habit Tracker " by Dimitra Christina Nikolaidou
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.youstayyoung.data.BodyActionContract.BodyActionEntry;
import com.example.android.youstayyoung.data.BodyActionDBHelper;

/**
 * Displays list of body actions that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private BodyActionDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        android.support.design.widget.FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.android.youstayyoung.CatalogActivity.this, com.example.android.youstayyoung.EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BodyActionDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BodyActionEntry._ID,
                BodyActionEntry.COLUMN_BODY_NAME,
                BodyActionEntry.COLUMN_BODY_TIME,
                BodyActionEntry.COLUMN_BODY_DISTANCE,
                BodyActionEntry.COLUMN_BODY_CALORIES};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                BodyActionEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_body_action);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> body.
            // _id - name - time - distance - calories
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + "body actions.\n\n");
            displayView.append(BodyActionEntry._ID + " - " +
                    BodyActionEntry.COLUMN_BODY_NAME + " - " +
                    BodyActionEntry.COLUMN_BODY_TIME + " - " +
                    BodyActionEntry.COLUMN_BODY_DISTANCE + " - " +
                    BodyActionEntry.COLUMN_BODY_CALORIES + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BodyActionEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BodyActionEntry.COLUMN_BODY_NAME);
            int timeColumnIndex = cursor.getColumnIndex(BodyActionEntry.COLUMN_BODY_TIME);
            int distanceColumnIndex = cursor.getColumnIndex(BodyActionEntry.COLUMN_BODY_DISTANCE);
            int caloriesColumnIndex = cursor.getColumnIndex(BodyActionEntry.COLUMN_BODY_CALORIES);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                int currentDistance = cursor.getInt(distanceColumnIndex);
                int currentCalories = cursor.getInt(caloriesColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentTime + " - " +
                        currentDistance + " - " +
                        currentCalories));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded body actions data into the database. For debugging purposes only.
     */
    private void insertBodyAction() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BodyActionEntry.COLUMN_BODY_NAME, "Static Bicycle");
        values.put(BodyActionEntry.COLUMN_BODY_TIME, 30);
        values.put(BodyActionEntry.COLUMN_BODY_DISTANCE, 5000);
        values.put(BodyActionEntry.COLUMN_BODY_CALORIES, 170);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(BodyActionEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBodyAction();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}