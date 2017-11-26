package com.vagapov.amir.otzovik.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vagapov.amir.otzovik.R;
import com.vagapov.amir.otzovik.model.Place;
import com.vagapov.amir.otzovik.model.PlaceList;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;


public class ListOfPlacesFragment extends Fragment {



    public static final String SEND_ID = "send_id";

    private RecyclerView recyclerView;
    private List<Place> placeList;
    private ListOfPlacesFragment.PlaceAdapter adapter;
    public static boolean showSubtitle;

    FragmentCoordinator callBackActivity;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_place_detail_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_favourite_places:
                showSubtitle = !showSubtitle;
                if(showSubtitle) {
                    updateAdapter(PlaceList.getOurInstance(getContext()).getFavoritePlaces());
                }else {
                    updateAdapter(PlaceList.getOurInstance(getContext()).getPlaces());
                }
                return true;
            case R.id.all_places:
                showSubtitle = false;
                updateAdapter(placeList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(List<Place> places) {
        int count = places.size();
        String subtitle = getResources().getQuantityString(R.plurals.fav_places_count, count, count);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(!showSubtitle) {
            subtitle = null;
        }
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackActivity = (FragmentCoordinator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_places, container, false);

        placeList = PlaceList.getOurInstance(getContext()).getPlaces();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateAdapter(placeList);


        FloatingActionButton fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_ok);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubtitle = false;
                callBackActivity.addPlace();
            }
        });
        return view;

    }

    private void updateAdapter(List<Place> places) {

        adapter = new PlaceAdapter(places, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(places != null) {
            updateSubtitle(places);
        }
    }



    private class PlaceAdapter extends RecyclerView.Adapter<ListOfPlacesFragment.PlaceViewHolder> {

        private List<Place> placeList;
        private Context context;

        public PlaceAdapter(List<Place> placeList, Context context) {
            this.placeList = placeList;
            this.context = context;
        }

        @Override
        public ListOfPlacesFragment.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.list_item_layout, parent, false);
            return new ListOfPlacesFragment.PlaceViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ListOfPlacesFragment.PlaceViewHolder holder, int position) {
            Place place = placeList.get(position);
            holder.bind(place);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            if (placeList != null) {
                return placeList.size();
            }
            return 0;
        }



    }

    private  class PlaceViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView adressTextView;
        private TextView placeTypeTextView;
        private ImageView photoImageView;
        private RatingBar ratingBar;
        private ImageButton addToFavoriteButton;
        private Place mPlace;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            adressTextView = (TextView) itemView.findViewById(R.id.list_item_adress_text_view);
            placeTypeTextView = (TextView) itemView.findViewById(R.id.list_item_place_type);
            photoImageView = (ImageView) itemView.findViewById(R.id.list_item_image_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.list_item_rating_bar);
            addToFavoriteButton = (ImageButton) itemView.findViewById(R.id.list_item_add_to_favorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showSubtitle = false;
                    Log.d("ttt", mPlace.getPlaceId().toString());
                    callBackActivity.createViewPager(mPlace.getPlaceId());
                }
            });
        }

        public void bind(final Place place){
            mPlace = place;
            titleTextView.setText(mPlace.getTitle());
            adressTextView.setText(place.getAdress());
            placeTypeTextView.setText(place.getPlaceType());
            ratingBar.setRating(Integer.valueOf(place.getRating()));
            photoImageView.setImageResource(place.getPhoto());
            if (place.isAddToFavourite() == true) {
                addToFavoriteButton.setImageResource(R.drawable.ic_favorite_pink_24dp);
            }
            addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (place.isAddToFavourite() == false) {
                        place.setAddToFavourite(true);
                        addToFavoriteButton.setImageResource(R.drawable.ic_favorite_pink_24dp);
                        Toasty.info(getContext(), place.getTitle() + " "
                                + getString(R.string.add_tofavorite), Toast.LENGTH_SHORT, true).show();
                    } else {
                        place.setAddToFavourite(false);
                        addToFavoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                        Toasty.info(getContext(), place.getTitle() + " " +
                                getString(R.string.remove_tofavorite), Toast.LENGTH_SHORT, true).show();
                    }
                }
            });


        }

    }
}



