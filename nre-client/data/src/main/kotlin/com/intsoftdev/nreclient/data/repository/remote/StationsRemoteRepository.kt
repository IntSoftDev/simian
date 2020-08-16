package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable

internal interface StationsRemoteRepository {
    fun getAllStations() : Observable<List<StationModel>>
    fun getStationsVersion(): Observable<List<Version>>
}