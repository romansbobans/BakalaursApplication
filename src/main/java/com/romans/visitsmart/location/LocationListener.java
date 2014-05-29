package com.romans.visitsmart.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import com.romans.visitsmart.utils.DevLog;
import com.romans.visitsmart.utils.Prefs;

/**
 * Created by Romans on 07/05/14.
 */
public class LocationListener implements android.location.LocationListener {

    private LocationCallback callback;
    private Context context;

    public LocationListener(Context ctx, LocationCallback callback)
    {
        context = ctx;
        this.callback = callback;

    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        editor.putString(Prefs.LAST_REGISTERED_LOCATION, location.getLatitude() + "," + location.getLongitude());
        editor.commit();
        DevLog.e("TAAG", location);

        if (callback != null)
        {
            callback.updateLocationChanged(location);
        }
    }


    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
