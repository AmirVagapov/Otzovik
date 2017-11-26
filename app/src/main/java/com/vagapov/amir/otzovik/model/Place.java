package com.vagapov.amir.otzovik.model;

import android.graphics.Bitmap;

import java.util.UUID;

public class Place {
    private String title;
    private String adress;
    private String telephone;
    private String workTime;
    private String rating;
    private String placeType;
    private String description;
    private UUID placeId;


    private boolean addToFavourite;


    private int photo;
    private Bitmap placePhoto;
    // TODO: 15.10.2017 Добавить координаты

    public Place(String title, String adress, String telephone, String workTime,
                 String rating, String placeType, String description, UUID id) {
        this.title = title;
        this.adress = adress;
        this.telephone = telephone;
        this.workTime = workTime;
        this.rating = rating;
        this.placeType = placeType;
        this.description = description;
        placeId = id;
        addToFavourite = false;
    }

    public Place(String title, String adress, String telephone, String workTime,
                 String rating, String placeType, String description){
        this(title, adress, telephone, workTime, rating, placeType, description, UUID.randomUUID());
    }

    public Place(String title, String adress, String telephone, String workTime,
                 String rating, String placeType, String description, int photo) {
        this.title = title;
        this.adress = adress;
        this.telephone = telephone;
        this.workTime = workTime;
        this.rating = rating;
        this.placeType = placeType;
        this.description = description;
        this.photo = photo;
        placeId = UUID.randomUUID();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public boolean isAddToFavourite() {
        return addToFavourite;
    }

    public void setAddToFavourite(boolean addToFavourite) {
        this.addToFavourite = addToFavourite;
    }

    public UUID getPlaceId() {
        return placeId;
    }

}
