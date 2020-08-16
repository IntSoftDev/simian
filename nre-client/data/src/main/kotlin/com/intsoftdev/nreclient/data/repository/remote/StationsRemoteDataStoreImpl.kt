package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable

internal class StationsRemoteDataStoreImpl(
        private val stationsRemoteRepository: StationsRemoteRepository)
    : StationsRemoteDataStore {

    override fun getAllStationsFromServer(): Observable<List<StationModel>> {
        return stationsRemoteRepository.getAllStations()
    }

    override fun getStationsVersion(): Observable<List<Version>>  {
        return stationsRemoteRepository.getStationsVersion()
    }
}