package com.ee461l.group11.silvous;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ee461l.group11.silvous.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.vision.barcode.Barcode;


import java.util.Date;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddEventBackEnd extends AppCompatActivity implements PlaceSelectionListener{

    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private TextView LongitudeTextView;
    private TextView LatitudeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        LatitudeTextView = (TextView) findViewById(R.id.addressLat);
        LongitudeTextView = (TextView) findViewById(R.id.addressLon);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Search a Location");
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);

    }


    public void buttonClicked(View v) {
        EditText titleEdit = (EditText) findViewById(R.id.titleID);
        String title = titleEdit.getText().toString();
        EditText desEdit = (EditText) findViewById(R.id.desID);

        String des = desEdit.getText().toString();

        /* If you want to work with a direct address String */
        EditText addrLat = (EditText) findViewById(R.id.addressLat);
        String addr = addrLat.getText().toString();
        EditText addrLon = (EditText) findViewById(R.id.addressLon);
        String addr2 = addrLon.getText().toString();

        EditText dateEdit = (EditText) findViewById(R.id.dateID);
        String date = dateEdit.getText().toString();

        double latitude = Double.parseDouble(addr);
        double longitude = Double.parseDouble(addr2);



        /* Event generation */
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date eventDate = new Date();
        try{
            eventDate = df.parse(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        eventDate.setYear(eventDate.getYear()+1900);
        eventDate.setMonth(eventDate.getMonth() + 1);



        SilvousEvent tempEve = new SilvousEvent(title, des, eventDate, latitude, longitude);
        SilvousFirebaseManager.addevent(tempEve);
        Intent resultIntent = new Intent(AddEventBackEnd.this, main_activity.class);
        /* ??? */
        resultIntent.putExtra(title, 1331);
        resultIntent.putExtra(des, 1331);
        //resultIntent.putExtra(addr, 1331);
        resultIntent.putExtra(date, 1331);


        finish();
    }
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(LOG_TAG, "Place Selected: " + place.getName());
        LatitudeTextView.setText(getString(R.string.formatted_place_data, place.getLatLng().latitude));
        LongitudeTextView.setText(getString(R.string.formatted_place_data, place.getLatLng().longitude));

    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }


}