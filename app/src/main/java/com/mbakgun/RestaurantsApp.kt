package com.mbakgun

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class RestaurantsApp : Application() {

    @Inject
    lateinit var timber: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber)
    }
}
