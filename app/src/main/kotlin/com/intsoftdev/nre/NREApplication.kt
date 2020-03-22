package com.intsoftdev.nre

import android.app.Application
import com.intsoftdev.nre.di.viewModelModule
import com.intsoftdev.railclient.NRESDKInitialiser
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NREApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@NREApplication)
            // declare modules
            modules(listOf(
                    viewModelModule))
        }
        NRESDKInitialiser(this).initialise()
    }
}