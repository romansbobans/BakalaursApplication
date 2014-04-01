package com.romans.visitsmart.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 01/04/14.
 */
public class WelcomeActivity extends BaseActivity {

    private DrawerLayout mDrawer;

    private ListView mDrawerContent;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        init();
        customizeActionBar();
        setupDrawerToggle();
        setupListeners();



    }

    private void init()
    {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerContent = (ListView) findViewById(R.id.left_drawer);
    }

    private void setupListeners()
    {
        mDrawerContent.setAdapter(new ArrayAdapter(this,
                R.layout.drawer_item, new String[]{"Tits", "Tits", "Tits2"} ));

        mDrawer.setDrawerListener(mDrawerToggle);
    }

    private void customizeActionBar(){
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    };

    private void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("Teest");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Teeeeeeeeeeeeee");
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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
