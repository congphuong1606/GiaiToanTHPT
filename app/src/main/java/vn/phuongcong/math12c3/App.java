package vn.phuongcong.math12c3;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by Ominext on 1/23/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "appId");
    }
}
