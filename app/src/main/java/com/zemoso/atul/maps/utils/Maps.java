package com.zemoso.atul.maps.utils;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;
import com.zemoso.atul.maps.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Maps extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_token));
    }
}
