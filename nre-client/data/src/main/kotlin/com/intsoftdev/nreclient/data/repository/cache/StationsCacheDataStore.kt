package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Stations.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
internal interface StationsCacheDataStore {

    fun getAllStationsFromCache(): Observable<List<StationEntity>>
    fun saveAllStationsToCache(stations: List<StationEntity>): Completable
    fun clearCachedStations(): Completable
    fun isCached(): Single<Boolean>
}