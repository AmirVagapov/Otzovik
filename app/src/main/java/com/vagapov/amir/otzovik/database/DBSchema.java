package com.vagapov.amir.otzovik.database;

/**
 * Created by Tom on 24.11.2017.
 */

public class DBSchema {
    public class DBTable{
        public static final String TABLE_NAME = "places";

        public class Columns{
            public static final String UUID = "id";
            public static final String PLACE_NAME = "name";
            public static final String ADRESS = "adress";
            public static final String PHONE_NUMBER = "phone";
            public static final String WORKTIME = "worktime";
            public static final String RATING = "rating";
            public static final String PLACE_TYPE = "placetype";
            public static final String DESCRIPTION = "description";
            public static final String FAVORITE = "place";
        }
    }
}
