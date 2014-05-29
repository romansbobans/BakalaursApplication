package com.romans.visitsmart.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.romans.visitsmart.utils.Extras;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Romans on 05/05/14.
 */
public class ObjectViewListFragment extends BaseFragment  implements LocationCallback, DistanceHandler {

    private static final String SAVED_STATE_CATEGORY = "saved_category";
    View root;
    ListView list;
    Category category;
    List<VisitObject> objects;

    LocationManager locationManager;

    private ObjectViewListFragment(Category selectedCategory)
    {
           this.category = selectedCategory;
    }

    public ObjectViewListFragment() {
    }

    public  static Fragment newInstance(Category selectedCategory)
    {
        Fragment frag = new ObjectViewListFragment(selectedCategory);
        return frag;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (category != null)
        {
            outState.putString(SAVED_STATE_CATEGORY, new Gson().toJson(category));
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = View.inflate(getActivity(), R.layout.visit_objects_list_fragment, null);
        list = (ListView) root.findViewById(R.id.visit_objcts_list);
        if (savedInstanceState == null)
        {
        }
        else if (category == null)
        {
            category = new Gson().fromJson(savedInstanceState.getString(SAVED_STATE_CATEGORY), Category.class);
        }
        if (category == null)
        {
            getActivity().finish();
            return root;
        }
        new BaseRequest(this).getObjects(category);



        //Get location of user
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 50, new com.romans.visitsmart.location.LocationListener(this.getActivity(), this));
        return root;
    }

    @Override
    public void onObjectsReceivedSuccess(VisitObject[] categories) {
        objects = Arrays.asList(categories);
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

    @Override
    public void onObjectsReceivedFailed(Response response) {
        super.onObjectsReceivedFailed(response);
    }


    @Override
    public void updateLocationChanged(Location location) {
           if (objects != null)
           {
               new DistanceRetriever(new LatLng(location.getLatitude(), location.getLongitude()), objects, this ).execute();
           }
    }

    @Override
    public void notifyDistancesRetrieved() {
        if (list != null)
        {
            ((ArrayAdapter)list.getAdapter()).notifyDataSetChanged();
        }
    }
}
