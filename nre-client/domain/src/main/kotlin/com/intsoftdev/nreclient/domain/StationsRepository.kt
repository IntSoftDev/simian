package com.intsoftdev.nreclient.domain

import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This specifies the operations that need to be implemented in the data layer
 */
interface StationsRepository {

    fun getAllStations() : Observable<List<StationModel>>
    fun saveAllStations(stations : List<StationModel>): Completable

    fun getModelFromCache(stationName : String?, crsCode: String?) : Observable<StationModel>

}