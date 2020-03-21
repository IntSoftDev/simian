package com.intsoftdev.railsdk

import android.app.Application
import com.intsoftdev.railsdk.di.DIComponent
import com.intsoftdev.railsdk.di.Di

class NRESDKInitialiser(private val application: Application) : DIComponent {
    fun initialise() {
        Di.init(application)
    }
}