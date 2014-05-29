package com.romans.visitsmart.activities;

import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.utils.Extras;

/**
 * Created by Romans on 06/05/14.
 */
public class ObjectMapActivity extends BaseActivity{

    private VisitObject visitObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
        {
            String rawObject = getIntent().getStringExtra(Extras.SELECTED_VISITOBJECT);
            visitObject = new Gson().fromJson(rawObject, VisitObject.class);

        }
        else
        {
            String rawObject = savedInstanceState.getString(Extras.SELECTED_VISITOBJECT);
            visitObject = new Gson().fromJson(rawObject, VisitObject.class);
        }
        setContentView(R.layout.map_fragment);


        GoogleMap mapView =  ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        mapView.setMyLocationEnabled(true);
        mapView.addMarker(new MarkerOptions().position(new LatLng(52.0, 24.1)));
    }

}
