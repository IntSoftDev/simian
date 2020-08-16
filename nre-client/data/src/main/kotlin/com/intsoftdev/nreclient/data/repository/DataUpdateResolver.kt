package com.intsoftdev.nreclient.data.repository

import com.intsoftdev.nreclient.data.PreferencesHelper
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import io.reactivex.Observable

enum class DataUpdateAction {
    REMOTE,
    REFRESH,
    LOCAL
}

internal class DataUpdateResolver(private val preferencesHelper: PreferencesHelper) {

    companion object {
        private const val EXPIRATION_TIME_MS = (2 * 60 * 1000).toLong()
    }

    fun setLastUpdateTime(lastUpdate: Long) {
        preferencesHelper.lastUpdateTime = lastUpdate
    }

    /**
     * Checks whether the last update time exceeds the defined [EXPIRATION_TIME_MS] time.
     */
    fun doUpdate(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastUpdateTimeMillis()
        return currentTime - lastUpdateTime > Companion.EXPIRATION_TIME_MS
    }

    fun getUpdateAction(stationsCacheDataStore: StationsCacheDataStore): Observable<DataUpdateAction> {
        return stationsCacheDataStore.isEmpty().flatMapObservable { isEmpty ->
            if (isEmpty) {
                Observable.just(DataUpdateAction.REMOTE)
            } else {
                if (doUpdate()) {
                    Observable.just(DataUpdateAction.REFRESH)
                } else {
                    Observable.just(DataUpdateAction.LOCAL)
                }
            }
        }
    }

    /**
     * Get in millis, the last time the data was accessed.
     */
    private fun getLastUpdateTimeMillis(): Long {
        return preferencesHelper.lastUpdateTime
    }
}