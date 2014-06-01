package com.romans.visitsmart.activities;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.exceptions.LanguageNotFoundException;
import com.romans.visitsmart.utils.DevLog;
import com.romans.visitsmart.utils.Extras;

/**
 * Created by Romans on 06/05/14.
 */
public class ObjectMapActivity extends BaseActivity {

    private VisitObject visitObject;

    private GoogleMap mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            String rawObject = getIntent().getStringExtra(Extras.SELECTED_VISITOBJECT);
            DevLog.e(rawObject);
            visitObject = new GsonBuilder().create().fromJson(rawObject, VisitObject.class);

        } else {
            String rawObject = savedInstanceState.getString(Extras.SELECTED_VISITOBJECT);
            visitObject = new Gson().fromJson(rawObject, VisitObject.class);
        }
        setContentView(R.layout.map_fragment);


        mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        mapView.setMyLocationEnabled(true);

        MarkerOptions visitObjectMarker = new MarkerOptions().position(visitObject.getCoordinates());

        Marker visit = mapView.addMarker(visitObjectMarker);


        try {
            visit.setTitle(visitObject.getObjectDescriptions(getBaseContext()).getName());
            visit.showInfoWindow();
        } catch (LanguageNotFoundException e) {
            e.printStackTrace();
        }
        moveToCurrentLocation(visitObject.getCoordinates());

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String visit = new Gson().toJson(visitObject);
        outState.putString(Extras.SELECTED_VISITOBJECT, visit);
        super.onSaveInstanceState(outState);
    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mapView.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mapView.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }
}
