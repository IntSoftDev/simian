package com.intsoftdev.railclient.domain.repository

import com.intsoftdev.railclient.data.StationModel
import io.reactivex.Observable

interface StationsRepository {
    fun getAllStations() : Observable<List<StationModel>>
}