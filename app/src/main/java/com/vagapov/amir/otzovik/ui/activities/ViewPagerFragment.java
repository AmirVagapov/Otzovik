package com.vagapov.amir.otzovik.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagapov.amir.otzovik.R;
import com.vagapov.amir.otzovik.model.Place;
import com.vagapov.amir.otzovik.model.PlaceList;

import java.util.List;
import java.util.UUID;


public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private List<Place> places;
    private static final String VIEW_PAGER_ID_PLACE = "id_place";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        UUID uuid = (UUID) getArguments().getSerializable(VIEW_PAGER_ID_PLACE);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        if(!ListOfPlacesFragment.showSubtitle) {
            places = PlaceList.getOurInstance(getContext()).getPlaces();
        }else{
            places = PlaceList.getOurInstance(getContext()).getFavoritePlaces();
            ListOfPlacesFragment.showSubtitle = false;
        }

        FragmentManager fragmentManager = getChildFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Place place = places.get(position);
                return PlaceDetailFragment.newInstance(place.getPlaceId());
            }

            @Override
            public int getCount() {
                return places.size();
            }
        });

        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getPlaceId().equals(uuid)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        return view;
    }


    public static ViewPagerFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(VIEW_PAGER_ID_PLACE, id);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
