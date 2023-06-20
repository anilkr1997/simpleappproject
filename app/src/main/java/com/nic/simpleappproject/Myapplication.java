package com.nic.simpleappproject;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // init the data base libary Relam
        // read on this link https://realm.io/database-for-mobile-apps/
        Realm.init(this);
        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder().name(getString(R.string.app_name)).schemaVersion(1).allowQueriesOnUiThread(true).allowWritesOnUiThread(true).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }


}
