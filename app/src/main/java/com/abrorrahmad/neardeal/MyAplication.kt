package com.abrorrahmad.neardeal

import io.realm.Realm
import io.realm.RealmConfiguration

class MyAplication : android.app.Application(){

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("near.deal")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(configuration)

    }

}