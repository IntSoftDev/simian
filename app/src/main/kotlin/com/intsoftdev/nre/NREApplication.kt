package com.intsoftdev.nre

import android.app.Application
import com.intsoftdev.nre.di.viewModelModule
import com.intsoftdev.railclient.StationsClientInitializer
import com.intsoftdev.railclient.di.sdkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NREApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@NREApplication)
            modules(listOf(viewModelModule, sdkModule))
        }
        StationsClientInitializer(this).initialise()
    }
}