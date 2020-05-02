package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

internal class StationsCacheDataStoreImpl(
        private val stationsCache: StationsCache) : StationsCacheDataStore, StationsCache by stationsCache {

    override fun getAllStationsFromCache(): Observable<List<StationEntity>> = getStations()

    override fun saveAllStationsToCache(stations: List<StationEntity>): Completable {
        return saveStations(stations)
                .doOnComplete {
                    setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun clearCachedStations(): Completable = clearAll()

    override fun isCached() : Single<Boolean> = stationsCache.isDataCached()
}