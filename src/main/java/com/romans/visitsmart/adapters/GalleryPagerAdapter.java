package com.romans.visitsmart.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.VisitObject;

/**
 * Created by Romans on 20/05/14.
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private final VisitObject.ImagePair[] images;

    public GalleryPagerAdapter(VisitObject.ImagePair[] images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.fragment_gallery_pager, null);

        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
