package com.romans.visitsmart;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
