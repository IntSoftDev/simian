package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Stations.
 */
internal interface StationsRemoteDataStore {
    fun getAllStationsFromServer(): Observable<List<StationEntity>>
}