package com.farshidabz.spnote;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class SPNoteApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, getString(R.string.ad_mob_api_key));
    }
}
