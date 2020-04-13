package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.StationsProxyService
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Observable

/**
 * Remote implementation for retrieving Stations instances
 */
internal class StationsRemoteRepositoryImpl(
        private val stationsProxyService: StationsProxyService,
        private val stationMapper: StationModelMapper
) : StationsRemoteRepository {

    override fun getAllStations(): Observable<List<StationEntity>> {
        return stationsProxyService.getAllStations()
                .map {
                    val entities = mutableListOf<StationEntity>()
                    it.forEach { entities.add(stationMapper.mapToEntity(it)) }
                    entities
                }
    }
}