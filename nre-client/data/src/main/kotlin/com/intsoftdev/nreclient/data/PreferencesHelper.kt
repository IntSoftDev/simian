package com.intsoftdev.nreclient.data

import android.content.Context
import android.content.SharedPreferences


/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
internal class PreferencesHelper(context: Context) {

    companion object {
        private val PREF_NRE_PACKAGE_NAME = "com.intsoftdev.nreclient"

        private val PREF_KEY_LAST_UPDATE = "last_update"
    }

    private val stationPref: SharedPreferences

    init {
        stationPref = context.getSharedPreferences(PREF_NRE_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastUpdateTime: Long
        get() = stationPref.getLong(PREF_KEY_LAST_UPDATE, 0)
        set(lastUpdate) = stationPref.edit().putLong(PREF_KEY_LAST_UPDATE, lastUpdate).apply()
}
