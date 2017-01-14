package com.ee461l.group11.silvous;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Danny Vo on 11/27/2016.
 */

public class SilvousFirebaseManager {
    private static DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference();

    /**
     * Adds the event to the Firebase database
     * @param event
     */
    public static void addevent(SilvousEvent event)
    {
        int min = 000000;
        int max = 999999;
        Random generator = new Random(SystemClock.uptimeMillis());
        int randID = generator.nextInt((max-min)+ 1) + min;
        event.setEventID(randID);
        eventRef.child("events/" + Integer.toString(randID)).setValue(event);
    }

    /**
     * Accesses all events stored in Firebase database
     * @return  ArrayList<SilvousEvents>
     *  All events within the range preference of the user
     */
    public static ArrayList<SilvousEvent> loadNearbyEvents()
    {
        final ArrayList<SilvousEvent> nearbyEvents = new ArrayList<SilvousEvent>(0);
        FirebaseDatabase.getInstance().getReference().child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    SilvousEvent event = snapshot.getValue(SilvousEvent.class);
                    if(main_activity.currentUser.inRange(event))
                    {
                        /* For Firebase correctness debugging */
                        if(main_activity.debugFirebase){ System.out.println(event.getEventName()); }
                        nearbyEvents.add(event);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });


        return nearbyEvents;
    }

    /**
     * Removes all events from Firebase that have passed the current time
     */
    public static void cleanEvents()
    {
        FirebaseDatabase.getInstance().getReference().child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date todayDate = Calendar.getInstance().getTime();
                todayDate.setYear(todayDate.getYear()+1900);
                long today = todayDate.getTime();
                eventRef.child("today").setValue(today);
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    SilvousEvent event = snapshot.getValue(SilvousEvent.class);
                    if(event.getLongDate() < today)
                    {
                        eventRef.child("events/" + Integer.toString(event.getEventID())).removeValue();
                        /* For Firebase correctness debugging */
                        if(main_activity.debugFirebase){ System.out.println(event.getEventName()); }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    /**
     * Method that triggers when a SilvousEvent is updated by the user
     * Brute way of updating - finds object in Firebase and overwrites it
     * @param updatedEvent
     *  SilvousEvent object
     */
    public static void updateEvent(final SilvousEvent uEvent)
    {

        FirebaseDatabase.getInstance().getReference().child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SilvousEvent updatedEvent = uEvent;
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    SilvousEvent event = snapshot.getValue(SilvousEvent.class);
                    if(updatedEvent.getEventID() == event.getEventID())
                    {
                        //eventRef.child("events/" + Integer.toString(event.getEventID())).removeValue();
                        eventRef.child("events/" + Integer.toString(updatedEvent.getEventID())).setValue(updatedEvent);
                        /* For Firebase correctness debugging */
                        if(main_activity.debugFirebase){ System.out.println(event.getEventName()); }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }


    public static void registerUser(final GoogleInfo loginUser) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean userFound = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SilvousUser user = snapshot.getValue(SilvousUser.class);

                    if (user.getUserEmail().equals(loginUser.getUserID())) {

                        userFound = true;
                        main_activity.currentUser = new SilvousUser(user.getUserEmail());
                        Log.d("REGISTERUSER", "After::: " +main_activity.currentUser.getUserEmail());
                        break;
                    }
                }
                if (userFound == false) {
                    SilvousUser newUser = new SilvousUser(loginUser.getUserID(), loginUser.getUserEmail());
                    eventRef.child("users/" + loginUser.getUserID()).setValue(newUser);
                    main_activity.currentUser = newUser;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }
}
