package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import io.reactivex.Observable

internal interface StationsRemoteRepository {
    fun getAllStations() : Observable<List<StationEntity>>
}