package com.vagapov.amir.otzovik.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vagapov.amir.otzovik.R;
import com.vagapov.amir.otzovik.model.Place;
import com.vagapov.amir.otzovik.model.PlaceList;

import java.util.UUID;

import static com.vagapov.amir.otzovik.ui.activities.ListOfPlacesFragment.SEND_ID;


public class PlaceDetailFragment extends Fragment {


    private TextView titleTextView;
    private TextView adressTextView;
    private TextView phoneTextView;
    private TextView descriptionTextView;
    private TextView worktimeTextView;
    private ImageButton toCallImageButton;
    private TextView placeTypeTextView;
    private ImageView imageViewPhoto;
    private RatingBar ratingBar;
    private ImageView toolbarImage;

    private Bitmap imageBitMap;

    private int photo;
    private Place place;

    private final static String SAVE_BITMAP_PHOTO = "save_description_photo";
    private static final String SAVE_ID = "save_id";

    FragmentCoordinator callBackActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackActivity = (FragmentCoordinator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        if (getArguments() != null) {
            UUID uuid = (UUID) getArguments().getSerializable(SEND_ID);
            place = PlaceList.getOurInstance().getPlaceById(uuid);
        }
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);


        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);

        findViewMethod(view);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackActivity.onEditButtonClick(place.getPlaceId());

            }
        });

        if (phoneTextView != null) {
            toCallImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:" + phoneTextView.getText().toString()));
                    startActivity(callIntent);
                }
            });
        }


        setPlaceDescription();
        return view;
    }

    private void findViewMethod(View view) {
        placeTypeTextView = (TextView) view.findViewById(R.id.place_type_text_view);
        toCallImageButton = (ImageButton) view.findViewById(R.id.call_image_view);
        titleTextView = (TextView) view.findViewById(R.id.place_title);
        adressTextView = (TextView) view.findViewById(R.id.place_address);
        phoneTextView = (TextView) view.findViewById(R.id.place_telephone);
        worktimeTextView = (TextView) view.findViewById(R.id.place_worktime);
        descriptionTextView = (TextView) view.findViewById(R.id.place_description);
        ratingBar = (RatingBar) view.findViewById(R.id.place_detail_rating_bar);
        toolbarImage = (ImageView) view.findViewById(R.id.place_detail_toolbar_photo);
    }



    private void setPlaceDescription() {


            titleTextView.setText(place.getTitle());
            adressTextView.setText(place.getAdress());
            phoneTextView.setText(place.getTelephone());
            worktimeTextView.setText(place.getWorkTime());
            ratingBar.setRating(Integer.valueOf(place.getRating()));
            placeTypeTextView.setText(place.getPlaceType());
            descriptionTextView.setText(place.getDescription());
            toolbarImage.setImageResource(place.getPhoto());

    }

    public static PlaceDetailFragment newInstance(UUID uuid) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEND_ID, uuid);
        fragment.setArguments(args);
        return fragment;
    }
}





