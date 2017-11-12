package com.vagapov.amir.otzovik.model;

import com.vagapov.amir.otzovik.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PlaceList {

    private static PlaceList ourInstance = null;

    private List<Place> places;

    private PlaceList() {
        initPlacesList();
    }

    public static PlaceList getOurInstance() {
        if (ourInstance == null) {
            ourInstance = new PlaceList();
            return ourInstance;
        } else {
            return ourInstance;
        }

    }

    public List<Place> getPlace() {
        return places;
    }

    public List<Place> getFavoritePlaces(){
        List<Place> favPlaces = new ArrayList<>();
        for(Place place : places){
            if(place.isAddToFavourite()){
                favPlaces.add(place);
            }
        }
        return favPlaces;
    }


    private void initPlacesList() {
        places = new ArrayList<>();

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
                "2542311", "10:00 - 04:00", "3", "Ресторан", "Ресторан", R.drawable.gastro));

    }

    public Place getPlaceById(UUID id){
        for(Place place : places){
            if(place.getPlaceId().equals(id)){
                return place;
            }
        }
        return null;
    }
}
