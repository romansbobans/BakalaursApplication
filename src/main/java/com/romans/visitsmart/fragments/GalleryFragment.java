package com.romans.visitsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.adapters.GalleryPagerAdapter;
import com.romans.visitsmart.adapters.PagerContainer;
import com.romans.visitsmart.dao.VisitObject;

/**
 * Created by Romans on 20/05/14.
 */
public class GalleryFragment extends BaseFragment{


    private VisitObject.ImagePair[] images;
    private PagerContainer mContainer;
    private static final String IMAGE_ARG = "arguments.images";


    public static Fragment newInstance(VisitObject.ImagePair[] images) {

        GalleryFragment fragment = new GalleryFragment();
        Bundle arguments = new Bundle();
        arguments.putString(IMAGE_ARG, new Gson().toJson(images));
        fragment.setArguments(arguments);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, null);

        mContainer = (PagerContainer) view.findViewById(R.id.gallery_view_pager);

        if (savedInstanceState == null )
        {
            images = new Gson().fromJson(getArguments().getString(IMAGE_ARG), VisitObject.ImagePair[].class);
        }
        else
        {
            images = new Gson().fromJson(savedInstanceState.getString(IMAGE_ARG), VisitObject.ImagePair[].class);
        }
        ViewPager pager = mContainer.getViewPager();
        pager.setAdapter(new GalleryPagerAdapter(images));
        pager.setPageMargin(0);
        pager.setOffscreenPageLimit(pager.getAdapter().getCount());
        pager.setClipChildren(false);
        pager.setCurrentItem(pager.getAdapter().getCount()/2);


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IMAGE_ARG, new Gson().toJson(images));
    }
}
