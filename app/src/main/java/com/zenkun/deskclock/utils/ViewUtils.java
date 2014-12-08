package com.zenkun.deskclock.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zenkun.deskclock.BuildConfig;
import com.zenkun.deskclock.Utils;
import com.zenkun.deskclock.preferences.PreferenceApp;

/**
 * Created by zen on 04/12/2014.
 */
public class ViewUtils {
    public static void setupAds(Context context,AdView adView)
    {
        try {
            //try catch por que algunas extrañas veces los ads de google causan conflicto o.O
            if(!PreferenceApp.getPurchase(context))
            {
                adView.setVisibility(View.VISIBLE);
                AdRequest.Builder adRequest;
                adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                /*Location location = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if(location!=null)
                {
                    adRequest.setLocation(location);
                }*/
                if (BuildConfig.DEBUG) {
                    adRequest.addTestDevice(Utils.ADS_TEST_DEVICE);
                }
                adView.loadAd(adRequest.build());
            }else
            {
                adView.setVisibility(View.GONE);
            }
        }catch (Exception ex){ex.printStackTrace();}
    }
}
