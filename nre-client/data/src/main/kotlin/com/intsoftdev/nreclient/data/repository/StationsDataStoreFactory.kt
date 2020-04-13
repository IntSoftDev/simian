package com.intsoftdev.nreclient.data.repository

import com.intsoftdev.nreclient.data.repository.StationsDataStore
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore

internal class StationsDataStoreFactory(
        private val stationsCache: StationsCache,
        private val stationsCacheDataStore: StationsCacheDataStore,
        private val stationsRemoteDataStore: StationsRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    fun retrieveDataStore(isCached: Boolean): StationsDataStore {
        if (useCacheDataStore(isCached)) {
            return getCacheDataStore()
        }
        return getRemoteDataStore()
    }

    fun useCacheDataStore(isCached: Boolean) = isCached && !stationsCache.isExpired()

    /**
     * Return an instance of the Cache Data Store
     */
    fun getCacheDataStore() = stationsCacheDataStore

    /**
     * Return an instance of the Remote Data Store
     */
    fun getRemoteDataStore() = stationsRemoteDataStore
}