package com.ee461l.group11.silvous;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;



import java.text.SimpleDateFormat;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SilvousEvent event_1;
    SilvousEvent event_2;
    GoogleApiClient mGoogleApiClient;
    double myLat=0;
    double myLong=0;
    int semaphore = 0;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static int MY_PERMISSION_REQUEST_READ_FINE_LOCATION = 9001;
    private final static int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 9003;
    private LocationRequest mLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try {
            event_1 = new SilvousEvent("Event 1", "Description of Event 1", ft.parse("2016-11-15 11:30"), 30.289072, -97.741439);
            event_2 = new SilvousEvent("Event 2", "Description of Event 2", ft.parse("2016-11-15 9:30"), 30.289315, -97.739594);
        } catch (Exception e) {
            Log.e("CREATE", "Parsing error on init");
        }

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        /*
        LatLng e1 = new LatLng(event_1.getEventLocation().getX_coord(), event_1.getEventLocation().getY_coord());
        LatLng e2 = new LatLng(event_2.getEventLocation().getX_coord(), event_2.getEventLocation().getY_coord());
        mMap.addMarker(new MarkerOptions().position(e1).title(event_1.getEventName()));
        mMap.addMarker(new MarkerOptions().position(e2).title(event_2.getEventName()));
        */
        ArrayList<SilvousEvent> events = SilvousFirebaseManager.loadNearbyEvents();
        for(int i = 0; i<events.size(); i++){
            LatLng ll = new LatLng(events.get(i).getEventLocation().getX_coord(), events.get(i).getEventLocation().getY_coord());
            mMap.addMarker(new MarkerOptions().position(ll).title(events.get(i).getEventName()));
        }
        LatLng me = new LatLng(myLat, myLong);
        try {
            Thread.sleep(1000);
        }catch(Exception e){

        }
        me = new LatLng(myLat, myLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 11));
    }

    public void onStart() {
        mGoogleApiClient.connect();
        Log.d("OnStart", "Connecting...");
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        Log.d("OnStop", "Disconnecting...");
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
    }

    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Location services connected.");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else{
            Log.d("MYLOCATION", "last location is null");
            handleNewLocation(mLastLocation);
        }
    }

    public void onConnectionSuspended(int code){
        Log.i(TAG, "Location services suspended. Please reconnect.");
        Log.e("CONNECTION_SUSPENDED","Connection suspended?");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                // PERMISSION_REQUEST_ACCESS_FINE_LOCATION can be any unique int
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        myLat = location.getLatitude();
        myLong = location.getLongitude();
        LatLng latLng = new LatLng(myLat, myLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MYLOCATION", "Location changed!");
        handleNewLocation(location);
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
