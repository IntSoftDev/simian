package com.intsoftdev.railclient

import android.app.Application
import com.intsoftdev.railclient.di.DIComponent
import com.intsoftdev.railclient.di.Di
import timber.log.Timber

class StationsClientInitializer(private val application: Application) : DIComponent {
    fun initialise(enableLogging:Boolean = false) {
        if (enableLogging && BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Di.init(application)
    }
}