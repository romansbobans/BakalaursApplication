package com.romans.visitsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.romans.visitsmart.R;
import com.romans.visitsmart.adapters.GalleryPagerAdapter;
import com.romans.visitsmart.adapters.PagerContainer;
import com.romans.visitsmart.dao.VisitObject;

/**
 * Created by Romans on 20/05/14.
 */
public class GalleryFragment extends BaseFragment{


    private final VisitObject.ImagePair[] images;
    private PagerContainer mContainer;

    public GalleryFragment(VisitObject images) {
        this.images = images.getImagePairs();
    }


    public static Fragment newInstance(VisitObject images) {

        return new GalleryFragment(images);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, null);

        mContainer = (PagerContainer) view.findViewById(R.id.gallery_view_pager);

        ViewPager pager = mContainer.getViewPager();
        pager.setAdapter(new GalleryPagerAdapter(images));
        pager.setPageMargin(0);
        pager.setOffscreenPageLimit(pager.getAdapter().getCount());
        pager.setClipChildren(false);
        pager.setCurrentItem(pager.getAdapter().getCount()/2);

        return view;
    }

}
