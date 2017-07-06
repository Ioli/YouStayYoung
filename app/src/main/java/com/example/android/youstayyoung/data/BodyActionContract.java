package com.example.android.youstayyoung.data;


import android.provider.BaseColumns;

/**
 * API Contract for the Stay young app.
 */
public final class BodyActionContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BodyActionContract() {
    }

    /**
     * Inner class that defines constant values for the body database table.
     * Each entry in the table represents a single body action.
     */
    public static final class BodyActionEntry implements BaseColumns {

        /**
         * Name of database table for body actions
         */
        public final static String TABLE_NAME = "body";

        /**
         * Unique ID number for the body (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the body action.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_BODY_NAME = "name";

        /**
         * Time of the body action.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_BODY_TIME = "time";

        /**
         * Distance of the body action.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_BODY_DISTANCE = "distance";

        /**
         * Calories of the pet.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_BODY_CALORIES = "calories";


    }

}
