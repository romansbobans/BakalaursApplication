package com.romans.visitsmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences
{
	private static final String APP_PREFS_NAME = AppPreferences.class.getName();

	private SharedPreferences sharedPreferences;
	private Editor editor;


	public AppPreferences(Context context)
	{
		sharedPreferences = context.getSharedPreferences(APP_PREFS_NAME, Activity.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}


	public String getString(String prefName, String defValue)
	{
		return sharedPreferences.getString(prefName, defValue);
	}


	public void putString(String prefName, String prefValue)
	{
		editor.putString(prefName, prefValue);
		editor.commit();
	}

    public boolean getBoolean(String prefName, boolean defValue)
    {
        return sharedPreferences.getBoolean(prefName, defValue);
    }


    public void putBoolean(String prefName, boolean prefValue)
    {
        editor.putBoolean(prefName, prefValue);
        editor.commit();
    }
	
	public void putInt(String prefName, int prefValue)
	{
		editor.putInt(prefName, prefValue);
        editor.commit();
	}
	
	public int getInt(String prefName, int defValue)
	{
		return sharedPreferences.getInt(prefName, defValue);
	}

	public void putLong(String prefName, long prefValue)
	{
		editor.putLong(prefName, prefValue);
        editor.commit();
	}

	public long getLong(String prefName, long defValue)
	{
		return sharedPreferences.getLong(prefName, defValue);
	}

	public void remove(String prefName)
	{
		editor.remove(prefName);
		editor.commit();
	}

	public boolean contains(String prefName)
	{
		return sharedPreferences.contains(prefName);
	}


}

