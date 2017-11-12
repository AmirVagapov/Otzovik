package com.vagapov.amir.otzovik.ui.activities;


import java.util.UUID;

public interface FragmentCoordinator {


    void onEditButtonClick(UUID id);

    void onListReturn();

    void createViewPager(UUID id);

    void addPlace();
}
