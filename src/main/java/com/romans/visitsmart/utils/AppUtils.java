package com.romans.visitsmart.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import com.romans.visitsmart.R;

import java.util.Locale;

/**
 * Created by Romans on 13/05/14.
 */
public class AppUtils {
    public static final void changeLanguage(Context ctx)
    {
        Resources res = ctx.getResources();
        String language = new AppPreferences(ctx).getString(Prefs.LANGUAGE, res.getString(R.string.default_lang_LV));
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language.toLowerCase());
        res.updateConfiguration(conf, dm);
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
