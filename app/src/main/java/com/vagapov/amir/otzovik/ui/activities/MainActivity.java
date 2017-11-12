package com.vagapov.amir.otzovik.ui.activities;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vagapov.amir.otzovik.R;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements FragmentCoordinator {

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if(fragment == null){
            onListReturn();
        }

    }



    @Override
    public void onEditButtonClick(UUID id) {
        PlaceDetailEditFragment editFragment = PlaceDetailEditFragment.newInstance(id);
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){*/
            fragmentManager.beginTransaction().replace(R.id.container, editFragment).commit();
       /* } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().add(R.id.container_detail, editFragment).commit();
        }*/
    }

    @Override
    public void onListReturn() {
        ListOfPlacesFragment listFragment = new ListOfPlacesFragment();
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){*/
            fragmentManager.beginTransaction().replace(R.id.container, listFragment).commit();
        /*} else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().add(R.id.container_list, listFragment).commit();
        }*/
    }

    @Override
    public void createViewPager(UUID id) {
        ViewPagerFragment fragment = ViewPagerFragment.newInstance(id);
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){*/
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
        /*} else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().add(R.id.container_list, fragment).commit();
        }*/
    }

    @Override
    public void addPlace() {
        PlaceDetailEditFragment editFragment = PlaceDetailEditFragment.addPlace();
      /*  if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){*/
            fragmentManager.beginTransaction().replace(R.id.container, editFragment).addToBackStack(null).commit();
       /* } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().add(R.id.container_detail, editFragment).commit();
        }*/
    }
}
