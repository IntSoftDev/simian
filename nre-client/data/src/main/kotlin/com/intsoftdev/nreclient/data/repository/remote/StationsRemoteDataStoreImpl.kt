package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Observable

internal class StationsRemoteDataStoreImpl(
        private val stationsRemoteRepository: StationsRemoteRepository)
    : StationsRemoteDataStore {

    override fun getAllStationsFromServer(): Observable<List<StationEntity>> {
        return stationsRemoteRepository.getAllStations()
    }
}