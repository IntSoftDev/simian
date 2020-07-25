package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Methods for the data operations related to Stations.
 * Implemented by the data layer
 */
internal interface StationsCacheDataStore {

    fun getAllStationsFromCache(): Observable<List<StationEntity>>
    fun saveAllStationsToCache(stations: List<StationEntity>): Completable
    fun clearCachedStations(): Completable
    fun isCached(): Single<Boolean>
    fun getFromCache(stationName: String?, crsCode: String?) : Observable<StationEntity>
}