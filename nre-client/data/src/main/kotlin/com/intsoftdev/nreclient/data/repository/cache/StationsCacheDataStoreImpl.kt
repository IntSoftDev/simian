package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.repository.StationsDataStore
import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsCacheDataStore(
        private val stationsCache: StationsCache) : StationsDataStore {

    override fun saveAllStations(stations: List<StationEntity>): Completable {
        return stationsCache.saveStations(stations)
                .doOnComplete {
                    stationsCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getAllStations(): Observable<List<StationEntity>> {
        return stationsCache.getStations()
    }

    override fun clearStations(): Completable {
        return stationsCache.clearStations()
    }

    override fun isCached(): Single<Boolean> {
        return stationsCache.isCached()
    }
}