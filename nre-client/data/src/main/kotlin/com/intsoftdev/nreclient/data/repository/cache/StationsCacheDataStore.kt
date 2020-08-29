package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Methods for the data operations related to Stations.
 * Implemented by the data layer
 */
internal interface StationsCacheDataStore {

    fun getAllStationsFromCache(): Observable<List<StationEntity>>
    fun saveAllStationsToCache(
        version: VersionEntity,
        stations: List<StationEntity>
    ): Completable

    fun getVersionFromCache(): Observable<VersionEntity>
    fun clearCachedStations(): Completable
    fun isEmpty(): Single<Boolean>
    fun getFromCache(stationName: String?, crsCode: String?): Observable<StationEntity>
}