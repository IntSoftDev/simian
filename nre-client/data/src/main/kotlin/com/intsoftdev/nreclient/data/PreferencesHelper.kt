package com.intsoftdev.nreclient.data

import android.content.Context
import android.content.SharedPreferences


/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
internal class PreferencesHelper(context: Context) {

    companion object {
        private val PREF_NRE_PACKAGE_NAME = "com.intsoftdev.nreclient"

        private val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val bufferPref: SharedPreferences

    init {
        bufferPref = context.getSharedPreferences(PREF_NRE_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = bufferPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = bufferPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}
