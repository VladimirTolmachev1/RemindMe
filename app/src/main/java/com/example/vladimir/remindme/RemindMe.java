package com.example.vladimir.remindme;

import android.app.Application;

import com.example.vladimir.remindme.realm.Migrations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RemindMe extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("remindme.realm")
                .schemaVersion(1)
                .migration(new Migrations())
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
