package com.vagapov.amir.otzovik.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vagapov.amir.otzovik.model.Place;

import java.util.UUID;

import static com.vagapov.amir.otzovik.database.DBSchema.DBTable.Columns.*;

/**
 * Created by Tom on 24.11.2017.
 */

public class PlaceCursorWrapper extends CursorWrapper {

    public PlaceCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Place getPlace(){
        String uuid = getString(getColumnIndex(UUID));
        String name = getString(getColumnIndex(PLACE_NAME));
        String adress = getString(getColumnIndex(ADRESS));
        String phone = getString(getColumnIndex(PHONE_NUMBER));
        String worktime = getString(getColumnIndex(WORKTIME));
        String rating = getString(getColumnIndex(RATING));
        String placetype = getString(getColumnIndex(PLACE_TYPE));
        String description = getString(getColumnIndex(DESCRIPTION));


        Place place = new Place(name, adress, phone, worktime, rating, placetype, description, java.util.UUID.fromString(uuid));
        return place;
    }
}
