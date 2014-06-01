package com.romans.visitsmart.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.activities.VisitObjectDescriptionActivity;
import com.romans.visitsmart.adapters.VisitObjectListAdapter;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.location.LocationCallback;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.handler.DistanceHandler;
import com.romans.visitsmart.networking.maps.DistanceRetriever;
import com.romans.visitsmart.networking.traffic.Response;
import com.romans.visitsmart.utils.DevLog;
import com.romans.visitsmart.utils.Extras;

/**
 * Created by Romans on 05/05/14.
 */
public class ObjectViewListFragment extends BaseFragment implements LocationCallback, DistanceHandler {

    View root;
    ListView list;
    Category category;
    VisitObject[] objects;

    LocationManager locationManager;


    public ObjectViewListFragment() {
    }

    public static Fragment newInstance(Category selectedCategory) {
        Fragment frag = new ObjectViewListFragment();
        Bundle args = new Bundle();
        args.putString(Extras.SELECTED_CATEGORY, new Gson().toJson(selectedCategory));
        frag.setArguments(args);
        return frag;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson g = new Gson();
        if (category != null) {
            outState.putString(Extras.SELECTED_CATEGORY, g.toJson(category));

        }
        if (objects != null) {
            DevLog.e("Saving objects");
            outState.putString(Extras.SELECTED_OBJECTS_ARRAY, g.toJson(objects));
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = View.inflate(getActivity(), R.layout.visit_objects_list_fragment, null);
        list = (ListView) root.findViewById(R.id.visit_objcts_list);


        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(Extras.SELECTED_CATEGORY))) {
            category = new Gson().fromJson(getArguments().getString(Extras.SELECTED_CATEGORY), Category.class);
        }
        if (savedInstanceState != null && !TextUtils.isEmpty(savedInstanceState.getString(Extras.SELECTED_OBJECTS_ARRAY))) {

                objects = new Gson().fromJson(savedInstanceState.getString(Extras.SELECTED_OBJECTS_ARRAY), VisitObject[].class);

        }
        if (objects == null)
        //Load views
        {            DevLog.e("Loading objects");

            new BaseRequest(this).getObjects(category);
            showLoadingDialog();
        }
        else
        {            DevLog.e("Retrieving objects");

            onObjectsReceivedSuccess(objects);
        }

        //Get location of user
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 50, new com.romans.visitsmart.location.LocationListener(this.getActivity(), this));
        return root;
    }

    @Override
    public void onObjectsReceivedSuccess(VisitObject[] categories) {

        dismissLoadingDialog();
        if (list != null && categories != null) {
            objects = categories;
            if (objects == null || getActivity() == null) {
                return;
            }
            list.setAdapter(new VisitObjectListAdapter(getActivity(), objects));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VisitObject object = (VisitObject) parent.getAdapter().getItem(position);
                    Intent descriptionActivityIntent = new Intent(getActivity(), VisitObjectDescriptionActivity.class);
                    descriptionActivityIntent.putExtra(Extras.SELECTED_VISITOBJECT, new Gson().toJson(object));
                    startActivity(descriptionActivityIntent);
                }
            });
        }

    }

    @Override
    public void onObjectsReceivedFailed(Response response) {
        dismissLoadingDialog();
        showDefaultNetworkError(response);
        getActivity().finish();

    }


    @Override
    public void updateLocationChanged(Location location) {
        if (objects != null) {
            new DistanceRetriever(new LatLng(location.getLatitude(), location.getLongitude()), objects, this).execute();
        }
    }

    @Override
    public void notifyDistancesRetrieved() {
        if (list != null && list.getAdapter() != null) {
            ((ArrayAdapter) list.getAdapter()).notifyDataSetChanged();
        }
    }

}
