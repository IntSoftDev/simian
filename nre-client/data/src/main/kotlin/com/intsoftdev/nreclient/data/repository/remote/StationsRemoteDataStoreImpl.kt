package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsRemoteCacheDataStore(
        private val stationsRemoteRepository: StationsRemoteRepository)
    : StationsRemoteDataStore {

    override fun getAllStations(): Observable<List<StationEntity>> {
        return stationsRemoteRepository.getAllStations()
    }
}