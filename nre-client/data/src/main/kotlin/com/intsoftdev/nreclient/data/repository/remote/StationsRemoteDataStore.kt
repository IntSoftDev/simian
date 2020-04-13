package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.repository.StationsDataStore
import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

internal class StationsRemoteDataStore(
        private val stationsRemoteRepository: StationsRemoteRepository)
    : StationsDataStore {

    override fun saveAllStations(stations: List<StationEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getAllStations(): Observable<List<StationEntity>> {
        return stationsRemoteRepository.getAllStations()
    }

    override fun clearStations(): Completable {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}