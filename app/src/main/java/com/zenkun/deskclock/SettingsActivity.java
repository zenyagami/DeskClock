/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zenkun.deskclock;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.zenkun.deskclock.preferences.PreferenceApp;


/**
 * Settings for the Alarm Clock.
 */
public class SettingsActivity extends ActionBarActivity implements BillingProcessor.IBillingHandler, FragmentSettings.onAdsClickListener {

    private BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
        }
        bp = new BillingProcessor(this,Utils.IAP_KEY,this);
        FragmentSettings fragmentSettings = new FragmentSettings();
        fragmentSettings.setOnAdsClickListener(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content,fragmentSettings).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getSupportActionBar()!=null)
        {
           // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Utils.getCurrentHourColor()));
        }
        getWindow().getDecorView().setBackgroundColor(Utils.getCurrentHourColor());
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        MenuItem help = menu.findItem(R.id.menu_item_help);
        if (help != null) {
            Utils.prepareHelpMenuItem(this, help);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
        PreferenceApp.setPurchase(getApplicationContext(), true);
        String toast=getString(R.string.success_purchase_ad_free) ;
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int i, Throwable throwable) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onRemoveAdsClick() {
        try {
            bp.purchase(Utils.AD_FREE_SKU);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
