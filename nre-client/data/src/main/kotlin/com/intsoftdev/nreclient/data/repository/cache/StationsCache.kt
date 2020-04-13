package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Stations.
 */
internal interface StationsCache {

    /**
     * Clear all theStations from the cache.
     */
    fun clearStations(): Completable

    /**
     * Save a given list of Stations to the cache.
     */
    fun saveStations(stations: List<StationEntity>): Completable

    /**
     * Retrieve a list of Stations from the cache.
     */
    fun getStations(): Observable<List<StationEntity>>

    /**
     * Check whether there is a list of Stations stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean
}