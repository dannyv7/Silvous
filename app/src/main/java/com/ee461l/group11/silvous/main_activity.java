package com.ee461l.group11.silvous;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageButton;
import android.util.Log;

import java.lang.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.ee461l.group11.silvous.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AndroidAppUri;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.params.HttpParams;



public class main_activity extends AppCompatActivity {
    //public static final String servletURL = "http://10.0.2.2:8080/java-servlet_backend/EventServlet";
    //public static final String eventServletURL = "http://silvous-150723.appspot.com/EventServlet";
    public static final String firebaseURL = "https://silvous-150723.firebaseio.com/";
    public static SilvousUser currentUser;
    public static final Boolean debugFirebase = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    public void onBackPressed(){
        Intent resultIntent = new Intent(main_activity.this, main_activity.class);
        startActivity(resultIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


         /* Test Firebase shit */
        if(debugFirebase) {
            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference();

            /* Generate dates */

            /* U wanna fuck with adding shit here u go */
            SilvousEvent testEvent = new SilvousEvent("Test 0", "Please work", new GregorianCalendar(2014 + 1900, Calendar.FEBRUARY, 11, 9, 9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent);
            SilvousEvent testEvent1 = new SilvousEvent("Test 1", "Please work", new GregorianCalendar(1990 + 1900, Calendar.NOVEMBER, 20, 9 ,9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent1);
            SilvousEvent testEvent2 = new SilvousEvent("Test 2", "Please work", new GregorianCalendar(1999 + 1900, Calendar.DECEMBER, 25, 9, 9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent2);
            SilvousEvent testEvent3 = new SilvousEvent("Test 3", "Please work", new GregorianCalendar(2017 + 1900, Calendar.JANUARY, 1, 9, 9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent3);
            SilvousEvent testEvent4 = new SilvousEvent("Test 4", "Please work", new GregorianCalendar(2016 + 1900, Calendar.NOVEMBER, 29, 9, 9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent4);
            SilvousEvent testEvent5 = new SilvousEvent("Test 5", "Please work", new GregorianCalendar(2016 + 1900, Calendar.NOVEMBER, 26, 9, 9, 9).getTime(), 6, 9);
            SilvousFirebaseManager.addevent(testEvent5);

            /* Try removing stuff that have already occurred */
            SilvousFirebaseManager.cleanEvents();

            /* Try removing stuff that have already occured */
            //SilvousFirebaseManager.cleanEvents();

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_header);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        FloatingActionButton pack = (FloatingActionButton) findViewById(R.id.pack);
        pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), AddEventBackEnd.class);
                startActivityForResult(myIntent, 1331);
                Log.d("MAINACTIVITY", "Add event button");
            }
        });

        FloatingActionButton maplaunch = (FloatingActionButton) findViewById(R.id.maplaunch);
        maplaunch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(myIntent, 8492);
                Log.d("MAINACTIVITY", "o to map.");
            }
        });

        ImageButton sign_out = (ImageButton) findViewById(R.id.sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), signin_activity.class);
                startActivityForResult(myIntent, 9001);
                Log.d("MAINACTIVITY", "Signing out button press");
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(currentUser==null){
            Log.e("MAINACTIVITY", "User still not initialized!");
        }
        Log.i("MAINACTIVITY", "User: " + currentUser.getUserEmail());
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1331): {
                if (resultCode == main_activity.RESULT_OK) {
                    String title = data.getDataString();

                    //BASICALLY SEND "newText" into "addEvent" which is in TreeManager.java
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doing_things, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("main_activity Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
