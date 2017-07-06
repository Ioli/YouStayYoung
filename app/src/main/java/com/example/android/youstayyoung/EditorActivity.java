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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.example.android.youstayyoung.data.BodyActionContract.BodyActionEntry;
import com.example.android.youstayyoung.data.BodyActionDBHelper;


/**
 * Allows user to create a new body action or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the body action's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the body action's time
     */
    private EditText mTimeEditText;

    /**
     * EditText field to enter the body action's distance
     */
    private EditText mDistanceEditText;

    /**
     * EditText field to enter the body action's burned energy
     */
    private EditText mCaloriesEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_bodyaction_name);
        mTimeEditText = (EditText) findViewById(R.id.edit_bodyaction_time);
        mDistanceEditText = (EditText) findViewById(R.id.edit_bodyaction_distance);
        mCaloriesEditText = (EditText) findViewById(R.id.edit_bodyaction_calories);

    }


    /**
     * Get user input from editor and save new body action into database.
     */
    private void insertBodyAction() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String timeString = mTimeEditText.getText().toString().trim();
        String distanceString = mDistanceEditText.getText().toString().trim();
        String caloriesString = mCaloriesEditText.getText().toString().trim();

        int time = Integer.parseInt(timeString);
        int distance = Integer.parseInt(distanceString);
        int calories = Integer.parseInt(caloriesString);

        // Create database helper
        BodyActionDBHelper mDbHelper = new BodyActionDBHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and body action attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BodyActionEntry.COLUMN_BODY_NAME, nameString);
        values.put(BodyActionEntry.COLUMN_BODY_TIME, time);
        values.put(BodyActionEntry.COLUMN_BODY_DISTANCE, distance);
        values.put(BodyActionEntry.COLUMN_BODY_CALORIES, calories);

        // Insert a new row for body action in the database, returning the ID of that new row.
        long newRowId = db.insert(BodyActionEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving body action", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Body action saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save body action to database
                insertBodyAction();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
