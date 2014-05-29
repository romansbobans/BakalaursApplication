package com.romans.visitsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 20/05/14.
 */
public class GalleryItemFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_pager, null);
    }

    public static Fragment getInstance(int position) {
        return new GalleryItemFragment();
    }
}
