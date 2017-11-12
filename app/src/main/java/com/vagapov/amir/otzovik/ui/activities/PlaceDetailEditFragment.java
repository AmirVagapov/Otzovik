package com.vagapov.amir.otzovik.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vagapov.amir.otzovik.R;
import com.vagapov.amir.otzovik.model.Place;
import com.vagapov.amir.otzovik.model.PlaceList;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static com.vagapov.amir.otzovik.ui.activities.ListOfPlacesFragment.SEND_ID;


public class PlaceDetailEditFragment extends Fragment {

    private EditText titleEditText;
    private EditText adressEditText;
    private EditText phoneEditText;
    private EditText ratingEditText;
    private EditText descriptionEditText;
    private EditText worktimeEditText;
    private Spinner choosePlaceTypeSpinner;
    private ImageView imageViewEdit;
    private Bitmap imageBitMap;
    private Button completeButton;
    UUID uuid;
    Bundle extras;


    private static final int REQUEST_IMAGE = 1;


    private Place place;


    FragmentCoordinator callBackActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackActivity = (FragmentCoordinator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        if (getArguments() != null) {
            uuid = (UUID) getArguments().getSerializable(SEND_ID);
            place = PlaceList.getOurInstance().getPlaceById(uuid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail_edit, container, false);
        initUI(view);

        getInfo();
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOnNull() && place != null) {
                    setPlaceDescription();
                    //callBackActivity.onListReturn();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(PlaceDetailEditFragment.this).commit();
                } else if (checkOnNull()) {
                    PlaceList.getOurInstance().getPlace().add(new Place(titleEditText.getText().toString(),
                            adressEditText.getText().toString(), phoneEditText.getText().toString(),
                            worktimeEditText.getText().toString(), ratingEditText.getText().toString(),
                            choosePlaceTypeSpinner.getSelectedItem().toString(), descriptionEditText.getText().toString()));
                    //callBackActivity.onListReturn();
                    getActivity().getSupportFragmentManager().beginTransaction().remove(PlaceDetailEditFragment.this).commit();
                }

            }
        });

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE);
                }
            }
        });


        return view;
    }

    private void getInfo() {

            if(uuid != null) {
                titleEditText.setText(place.getTitle());
                adressEditText.setText(place.getAdress());
                phoneEditText.setText(place.getTelephone());
                worktimeEditText.setText(place.getWorkTime());
                ratingEditText.setText(place.getRating());
                descriptionEditText.setText(place.getDescription());
                switch (place.getPlaceType()) {
                    case "Театр":
                        choosePlaceTypeSpinner.setSelection(5);
                        break;
                    case "Клуб":
                        choosePlaceTypeSpinner.setSelection(4);
                        break;
                    case "Ресторан":
                        choosePlaceTypeSpinner.setSelection(3);
                        break;
                    case "Бар":
                        choosePlaceTypeSpinner.setSelection(2);
                        break;
                    case "Кинотеатр":
                        choosePlaceTypeSpinner.setSelection(1);
                        break;
                    default:
                        choosePlaceTypeSpinner.setSelection(0);
                        break;
                }
            }


        /*extras = getInfo.getExtras();
        if (extras != null) {
            imageBitMap = (Bitmap) extras.get(SEND_IMAGE);
            imageViewEdit.setImageBitmap(imageBitMap);
        }*/
    }

    private void setPlaceDescription() {

        place.setTitle(titleEditText.getText().toString());
        place.setAdress(adressEditText.getText().toString());
        place.setTelephone(phoneEditText.getText().toString());
        place.setWorkTime(worktimeEditText.getText().toString());
        place.setRating(ratingEditText.getText().toString());
        place.setPlaceType(choosePlaceTypeSpinner.getSelectedItem().toString());
        place.setDescription(descriptionEditText.getText().toString());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            extras = data.getExtras();
            imageBitMap = (Bitmap) extras.get("data");
            imageViewEdit.setImageBitmap(imageBitMap);
        }
    }

    private void initUI(View view) {
        choosePlaceTypeSpinner = (Spinner) view.findViewById(R.id.spinner_choose_type_place);
        titleEditText = (EditText) view.findViewById(R.id.title_edit_text);
        adressEditText = (EditText) view.findViewById(R.id.adress_edit_text);
        phoneEditText = (EditText) view.findViewById(R.id.phone_edit_text);
        ratingEditText = (EditText) view.findViewById(R.id.rating_edit_text);
        descriptionEditText = (EditText) view.findViewById(R.id.description_edit_text);
        worktimeEditText = (EditText) view.findViewById(R.id.worktime_edit_text);
        completeButton = (Button) view.findViewById(R.id.complete_edit_button);
        imageViewEdit = (ImageView) view.findViewById(R.id.image_view_edit_text);
    }

    private boolean checkOnNull() {
        if (!(titleEditText.getText().toString().equals("") || adressEditText.getText().toString().equals("") ||
                phoneEditText.getText().toString().equals("") || worktimeEditText.getText().toString().equals("")
                || ratingEditText.getText().toString().equals("") || descriptionEditText.getText().toString().equals(""))) {
            if (Integer.valueOf(ratingEditText.getText().toString()) > 5) {
                Toasty.error(getContext(), getString(R.string.wrong_rating),
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else {
            Toasty.error(getContext(), getString(R.string.null_description),
                    Toast.LENGTH_SHORT, true).show();
            return false;
        }
    }

    public static PlaceDetailEditFragment newInstance(UUID id) {
        PlaceDetailEditFragment fragment = new PlaceDetailEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEND_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlaceDetailEditFragment addPlace(){
        PlaceDetailEditFragment fragment = new PlaceDetailEditFragment();
        return fragment;
    }


}
