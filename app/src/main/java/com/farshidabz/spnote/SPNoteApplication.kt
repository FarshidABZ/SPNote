package com.farshidabz.spnote;

import android.app.Application;

// import com.google.android.gms.ads.MobileAds; // Commented: AdMob SDK removed to allow build without legacy Google Play services Ads

public class SPNoteApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // MobileAds.initialize(this, getString(R.string.ad_mob_api_key));
        // Commented: AdMob SDK removed to allow build without legacy Google Play services Ads
    }
}
