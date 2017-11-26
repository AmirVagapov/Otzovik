package com.vagapov.amir.otzovik.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.vagapov.amir.otzovik.R;
import com.vagapov.amir.otzovik.database.DBSchema;
import com.vagapov.amir.otzovik.database.PlaceCursorWrapper;
import com.vagapov.amir.otzovik.database.PlaceOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.vagapov.amir.otzovik.database.DBSchema.DBTable.Columns.*;


public class PlaceList {

    private static PlaceList ourInstance = null;
    private SQLiteDatabase mDatabase;
    private List<Place> places;

    private PlaceList(Context context) {
        initPlacesList();
        mDatabase = new PlaceOpenHelper(context).getWritableDatabase();
    }

    public static PlaceList getOurInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PlaceList(context);
            return ourInstance;
        } else {
            return ourInstance;
        }

    }


    public List<Place> getFavoritePlaces(){
        PlaceCursorWrapper cursor = queryPlaces(FAVORITE + " = ?", new String[]{"1"});
        List<Place> places = new ArrayList<>();

        try {
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                places.add(cursor.getPlace());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return places;
    }


    private void initPlacesList() {
       /* places = new ArrayList<>();

        places.add(new Place("Harat's", "г. Уфа, Верхнеторговая площадь, д. 1",
                "2376970", "12:00 - 06:00", "5", "Бар", "Ирландский паб", R.drawable.harats));
        places.add(new Place("The Bar", "г. Уфа, ул. Чернышевского, д 88",
                "2231430", "12:00 - 04:00", "5", "Клуб", "Ночной клуб", R.drawable.thebar1));
        places.add(new Place("Morris", "г. Уфа, ул. Гоголя, д. 60/1",
                "2343234", "12:00 - 04:00", "4", "Бар", "Ирландский паб", R.drawable.morris));
        places.add(new Place("Burger Heroes", "г. Уфа, уд. Карла Маркса, д. 37",
                "2354312", "12:00 - 00:00", "5", "Ресторан", "Легендарный московский ресторан", R.drawable.burgerheroes));
        places.add(new Place("Искра", "г. Уфа, пр. Октября, д. 92",
                "2376423", "09:00 - 01:00", "5", "Кинотеатр", "Кинотеатр", R.drawable.iskra));
        places.add(new Place("Нур", "г. Уфа, ул. 50 лет СССР, д. 36",
                "2376970", "10:00 - 20:00", "5", "Театр", "Татарский театр", R.drawable.nur));
        places.add(new Place("Music Hall 27", "г. Уфа, ул. Кирова, д. 27",
                "2376943", "12:00 - 06:00", "5", "Клуб", "Ресторан. Клуб", R.drawable.musichall));
        places.add(new Place("Gastro Gallery", "г. Уфа, ул. Ленина, д. 16",
                "2542311", "10:00 - 04:00", "3", "Ресторан", "Ресторан", R.drawable.gastro));*/

    }

    public Place getPlaceById(UUID id){
        String uuid = id.toString();
        PlaceCursorWrapper cursor = queryPlaces(UUID + " = ?", new String[]{uuid});
        try {
            if(cursor.getCount() == 0){
                return  null;
            }
            cursor.moveToFirst();

            return cursor.getPlace();
        } finally {
            cursor.close();
        }

    }

    public ArrayList<Place> getPlaces(){
        PlaceCursorWrapper cursor = queryPlaces(null, null);
        ArrayList<Place> places = new ArrayList<>();
        try {
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                places.add(cursor.getPlace());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return places;
    }

    private static ContentValues getContentValues(Place place){
        ContentValues values = new ContentValues();
        values.put(UUID, place.getPlaceId().toString());
        values.put(PLACE_NAME, place.getTitle());
        values.put(ADRESS, place.getAdress());
        values.put(PHONE_NUMBER, place.getTelephone());
        values.put(WORKTIME, place.getWorkTime());
        values.put(RATING, place.getRating());
        values.put(PLACE_TYPE, place.getPlaceType());
        values.put(DESCRIPTION, place.getDescription());
        values.put(FAVORITE, place.isAddToFavourite() ? 1 : 0);
        return values;
    }

    private PlaceCursorWrapper queryPlaces(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(DBSchema.DBTable.TABLE_NAME, null, whereClause, whereArgs,
                null, null, null);
        return new PlaceCursorWrapper(cursor);
    }

    public void addPlace(Place place){
        ContentValues values = getContentValues(place);
        mDatabase.insert(DBSchema.DBTable.TABLE_NAME, null, values);
    }

    public void updatePlace(Place place){
        ContentValues values = getContentValues(place);
        String id = place.getPlaceId().toString();
        mDatabase.update(DBSchema.DBTable.TABLE_NAME, values, UUID
                +  " = ?", new String[]{id});
    }


}
