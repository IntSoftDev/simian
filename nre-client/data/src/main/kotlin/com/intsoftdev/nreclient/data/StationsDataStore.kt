package com.intsoftdev.nreclient.data

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Stations.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
internal interface StationsDataStore {

    fun saveAllStations(stations: List<StationEntity>): Completable
    fun getAllStations(): Observable<List<StationEntity>>
    fun clearStations(): Completable
    fun isCached(): Single<Boolean>
}