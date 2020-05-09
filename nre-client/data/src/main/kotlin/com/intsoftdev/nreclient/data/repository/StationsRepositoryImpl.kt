package com.intsoftdev.nreclient.data.repository

import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStoreImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStoreImpl
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import java.lang.RuntimeException

internal class StationsRepositoryImpl(
        private val stationsCacheStoreImpl: StationsCacheDataStoreImpl,
        private val stationsRemoteStoreImpl: StationsRemoteDataStoreImpl,
        private val stationMapper: StationModelMapper)
    : StationsRepository, StationsCacheDataStore by stationsCacheStoreImpl, StationsRemoteDataStore by stationsRemoteStoreImpl {

    override fun getAllStations(): Observable<List<StationModel>> {
        Timber.d("getAllStations enter")
        var useCache: Boolean
        return isCached().flatMapObservable { cached ->
            useCache = cached && !stationsCacheStoreImpl.isCacheExpired()
            getAllStations(useCache)
                    .flatMap { entities -> Observable.just(entities.map { entity -> stationMapper.mapFromEntity(entity) }) }
                    .flatMap { stations ->
                        if (useCache) {
                            Timber.d("transformed stations")
                            Observable.just(stations)
                        } else
                            saveAllStations(stations).toSingle { stations }.toObservable()
                    }.onErrorReturn {
                        // TODO determine cache logic
                        throw(it)
                    }
        }
    }

    override fun saveAllStations(stations: List<StationModel>): Completable {
        Timber.d("saveAllStations enter")
        val stationEntities = mutableListOf<StationEntity>()
        stations.map { stationEntities.add(stationMapper.mapToEntity(it)) }
        return saveAllStationsToCache(stationEntities)
    }

    override fun getModelFromCache(stationName: String?, crsCode: String?): Observable<StationModel> {
        return stationsCacheStoreImpl.getFromCache(stationName, crsCode).map {
            stationMapper.mapFromEntity(it)
        }
    }

    internal fun getAllStations(refreshfromCache: Boolean): Observable<List<StationEntity>> =
            when (refreshfromCache) {
                true -> getAllStationsFromCache()
                false -> getAllStationsFromServer()
            }
}