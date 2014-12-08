package com.zenkun.deskclock.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zen on 07/12/2014.
 */
public class PreferenceApp {
    private static final String PREF_PURCHASE = "purchase_done";
    private static final String PREF_NAME = "clock_prefs";

    public static void setPurchase(Context context, boolean setPurchase) {
        SharedPreferences preferencias = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean(PREF_PURCHASE, setPurchase);
        editor.commit();
    }
    public static boolean getPurchase(Context context) {
        SharedPreferences preferencias = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        return preferencias.getBoolean(PREF_PURCHASE, false);
    }
}
