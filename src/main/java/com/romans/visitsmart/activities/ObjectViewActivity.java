package com.romans.visitsmart.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.fragments.ObjectViewListFragment;
import com.romans.visitsmart.utils.Extras;

/**
 * Created by Romans on 05/05/14.
 */
public class ObjectViewActivity extends BaseActivity{


    Category selectedCategory;
    private String FRAGMENT_TAG = "objects.fragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_view_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        String selectedCategoryRaw;
        if (savedInstanceState == null)
        {
            selectedCategoryRaw = getIntent().getExtras().getString(Extras.SELECTED_CATEGORY);
            selectedCategory = gson.fromJson(selectedCategoryRaw, Category.class);
        }
        else
        {
            selectedCategoryRaw = savedInstanceState.getString(Extras.SELECTED_CATEGORY);
            selectedCategory = gson.fromJson(selectedCategoryRaw, Category.class);

        }

        if (fragment == null)
        {
            fragment = ObjectViewListFragment.newInstance(selectedCategory);
        }
        else
        {
            fragment.getArguments().putString(Extras.SELECTED_CATEGORY, selectedCategoryRaw);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.object_view_content_holder, fragment, FRAGMENT_TAG);
        transaction.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Extras.SELECTED_CATEGORY, new Gson().toJson(selectedCategory));
        super.onSaveInstanceState(outState);
    }
}

