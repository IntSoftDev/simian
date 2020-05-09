package com.intsoftdev.nreclient.cache

import com.intsoftdev.nreclient.cache.db.StationsDatabase
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

internal class StationsCacheImpl(
        val stationsDatabase: StationsDatabase,
        private val preferencesHelper: PreferencesHelper) : StationsCache {

    companion object {
        private const val EXPIRATION_TIME_MS = (2 * 60 * 1000).toLong()
    }

    override fun clearAll(): Completable {
        return Completable.defer {
            stationsDatabase.cachedStationDao().clearStations()
            Completable.complete()
        }
    }

    override fun saveStations(stations: List<StationEntity>): Completable {
        Timber.d("saveStations enter")
        return Completable.defer {
            stationsDatabase.cachedStationDao().insertStations(stations)
            Timber.d("saveStations exit")
            Completable.complete()
        }
    }

    override fun getStations(): Observable<List<StationEntity>> {
        Timber.d("getStations enter")
        return Observable.defer {
            Observable.just(stationsDatabase.cachedStationDao().getStations())
        }
    }

    override fun isDataCached(): Single<Boolean> {
        return Single.defer {
            Single.just(stationsDatabase.cachedStationDao().getStations().isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Checks whether the current cached data exceeds the defined [EXPIRATION_TIME_MS] time.
     */
    override fun isCacheExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > Companion.EXPIRATION_TIME_MS
    }

    override fun getStation(name: String?, crs: String?): Observable<StationEntity> {
        return Observable.defer {
            Observable.just(stationsDatabase.cachedStationDao().getStations().first())
        }
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}