package com.intsoftdev.railclient

import android.app.Application
import com.intsoftdev.railclient.di.DIComponent
import com.intsoftdev.railclient.di.Di

class StationsClientInitializer(private val application: Application) : DIComponent {
    fun initialise() {
        Di.init(application)
    }
}