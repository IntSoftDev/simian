package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Stations.
 */
interface StationsCache {

    /**
     * Clear all theStations from the cache.
     */
    fun clearAll(): Completable

    /**
     * Save a given list of Stations to the cache.
     */
    fun saveStations(
        version: VersionEntity,
        stations: List<StationEntity>
    ): Completable


    /**
     * Retrieve a list of Stations from the cache.
     */
    fun getStations(): Observable<List<StationEntity>>

    fun getVersionDetails(): Observable<VersionEntity>

    /**
     * Check whether there is a list of Stations stored in the cache.
     *
     * @return true if no stations are cached otherwise false
     */
    fun isCacheEmpty(): Single<Boolean>

    /*
     */

    fun getStation(name: String?, crs: String?): Observable<StationEntity>
}