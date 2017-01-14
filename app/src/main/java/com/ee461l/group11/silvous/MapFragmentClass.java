
package com.ee461l.group11.silvous;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by hotsauce on 11/28/16.
 */


public class MapFragmentClass extends Fragment{
    private MapFragment googleMap;
    double xvariable;
    double yvariable;
    private LatLng myThing = new LatLng(xvariable,yvariable);

/*    public void processMap(View v){
        if(googleMap == null){
            googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapid));
        }
        if(googleMap != null){      //somehow need to get the add marker working
         //   googleMap.addMarker(new MarkerOptions().position(myThing).title("This is my event"));
         //   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myThing, 13));
        }
    }*/

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.map_fragment_layout, container, false);
        processMap(v);
        return v;
    }*/
}

