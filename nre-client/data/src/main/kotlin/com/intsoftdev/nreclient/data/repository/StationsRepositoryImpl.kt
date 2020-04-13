package com.intsoftdev.nreclient.data.repository

import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsRepository
import io.reactivex.Completable
import io.reactivex.Observable

internal class StationsRepositoryImpl(
        private val factory: StationsDataStoreFactory,
        private val stationMapper: StationModelMapper) : StationsRepository {

    override fun saveAllStations(stations: List<StationModel>): Completable {
        val stationEntities = mutableListOf<StationEntity>()
        stations.map { stationEntities.add(stationMapper.mapToEntity(it)) }
        return factory.getCacheDataStore().saveAllStations(stationEntities)
    }

    override fun getAllStations(): Observable<List<StationModel>> {
        var isCached = false
        return factory.getCacheDataStore().isCached()
                .flatMapObservable { it: Boolean ->
                    isCached = it
                    factory.retrieveDataStore(it).getAllStations()
                }
                .flatMap { it: List<StationEntity> ->
                    Observable.just(it.map { stationMapper.mapFromEntity(it) })
                }
                .flatMap {
                    if (factory.useCacheDataStore(isCached)) {
                        Observable.just(it)
                    } else {
                        saveAllStations(it).toSingle { it }.toObservable()
                    }
                }
    }
}