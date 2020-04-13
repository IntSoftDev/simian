package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.PreferencesHelper
import com.intsoftdev.nreclient.data.db.StationsDatabase
import com.intsoftdev.nreclient.data.mapper.StationEntityMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsCacheImpl(
        val stationsDatabase: StationsDatabase,
        private val entityMapper: StationEntityMapper,
        private val preferencesHelper: PreferencesHelper) : StationsCache {

    companion object {
        private const val EXPIRATION_TIME_MS = (2 * 60 * 1000).toLong()
    }

    override fun clearStations(): Completable {
        return Completable.defer {
            stationsDatabase.cachedStationDao().clearStations()
            Completable.complete()
        }
    }

    override fun saveStations(stations: List<StationEntity>): Completable {
        return Completable.defer {
            stations.forEach {
                stationsDatabase.cachedStationDao().insertStation(
                        entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    override fun getStations(): Observable<List<StationEntity>>  {
        return Observable.defer {
            Observable.just(stationsDatabase.cachedStationDao().getStations())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    override fun isCached(): Single<Boolean> {
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
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > Companion.EXPIRATION_TIME_MS
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}