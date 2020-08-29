package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsCacheDataStoreImpl(
    private val stationsCache: StationsCache
) : StationsCacheDataStore, StationsCache by stationsCache {

    override fun getAllStationsFromCache(): Observable<List<StationEntity>> = getStations()

    override fun saveAllStationsToCache(
        version: VersionEntity,
        stations: List<StationEntity>
    ): Completable {
        return saveStations(version, stations)
    }

    override fun getVersionFromCache(): Observable<VersionEntity> {
        return getVersionDetails()
    }

    override fun clearCachedStations(): Completable = clearAll()

    override fun isEmpty(): Single<Boolean> = isCacheEmpty()

    override fun getFromCache(stationName: String?, crsCode: String?): Observable<StationEntity> =
        stationsCache.getStation(null, crsCode)
}