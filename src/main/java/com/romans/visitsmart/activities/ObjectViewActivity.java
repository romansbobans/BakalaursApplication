package com.romans.visitsmart.activities;

import android.os.Bundle;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_view_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
        {
            String selectedCategoryRaw = getIntent().getExtras().getString(Extras.SELECTED_CATEGORY);
            selectedCategory = new Gson().fromJson(selectedCategoryRaw, Category.class);
        }
//        getActionBar().setTitle(selectedCategory.get);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.object_view_content_holder, ObjectViewListFragment.newInstance(selectedCategory));
        transaction.commit();




    }


}

