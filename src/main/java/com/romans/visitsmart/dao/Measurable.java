package com.romans.visitsmart.dao;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Romans on 07/05/14.
 */
public interface Measurable {

    void setDistance(String distance);
    LatLng getPosition();
}
