package com.romans.visitsmart.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.adapters.DrawerCategoriesAdapter;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.fragments.AboutFragment;
import com.romans.visitsmart.fragments.BackgroundFragment;
import com.romans.visitsmart.fragments.SettingsFragment;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.traffic.Response;
import com.romans.visitsmart.utils.DevLog;
import com.romans.visitsmart.utils.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romans on 01/04/14.
 */
public class WelcomeActivity extends BaseActivity implements SettingsFragment.OnSettingsChangedListener {

    private DrawerLayout mDrawer;

    private ListView mDrawerContent;

    private ActionBarDrawerToggle mDrawerToggle;

    private Category[] categories;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        init();
        customizeActionBar();
        setupDrawerToggle();
        setupListeners();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, BackgroundFragment.newInstance());
        transaction.commit();
        if (savedInstanceState == null)
        {
            showLoadingDialog();
        }
        else
        {
            String[] categories = savedInstanceState.getStringArray(Extras.LOADED_CATEGORIES);
            this.categories = new Category[categories.length];
            for (int i = 0; i < categories.length; i++)
            {
                this.categories[i] = new Gson().fromJson(categories[i], Category.class);
            }
            onCategoriesReceivedSuccess(this.categories);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        new BaseRequest(this).getAllCategories();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof BackgroundFragment)
        {
            super.onBackPressed();
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, BackgroundFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (categories == null)
        {
            return;
        }
       String[] cat = new String[categories.length];
        Gson gson = new Gson();

        for (int i = 0; i < categories.length; i++)
        {
            cat[i] = gson.toJson(categories[i]);
        }
        outState.putStringArray(Extras.LOADED_CATEGORIES, cat);
    }

    private void init()
    {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerContent = (ListView) findViewById(R.id.left_drawer);

    }

    private void setupListeners()
    {
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    private void customizeActionBar(){
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    };

    private void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                R.drawable.arrow_right, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(getString(R.string.drawer_closed_text));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(getString(R.string.drawer_opened_text));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    };


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
       mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategoriesReceivedSuccess(Category[] categories) {
        this.categories = categories;
        List cat = new ArrayList(categories.length);
        for (int i = 0; i < categories.length; i++)
        {
            cat.add(categories[i]);
        }
        dismissLoadingDialog();
        mDrawerContent.setAdapter(new DrawerCategoriesAdapter(this, cat ));
        mDrawerContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WelcomeActivity.this, ObjectViewActivity.class);


                Log.e("TAG", new Gson().toJson(parent.getAdapter().getItem(position)));
                intent.putExtra(Extras.SELECTED_CATEGORY, new Gson().toJson(parent.getAdapter().getItem(position)));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCategoriesReceivedFailed(Response response) {

    }

    public void onHelp(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, AboutFragment.newInstance());
        DevLog.e("HELP PRESSED");
        transaction.commit();
        mDrawer.closeDrawers();
    }

    public void onSettings(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, SettingsFragment.newInstance(this));
        transaction.commit();
        mDrawer.closeDrawers();
    }

    @Override
    public void onLanguageChanged() {
        onCategoriesReceivedSuccess(categories);
    }
}
