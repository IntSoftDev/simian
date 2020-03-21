package com.intsoftdev.railsdk.domain.repository

import com.intsoftdev.railsdk.data.StationModel
import io.reactivex.Observable

interface StationsRepository {
    fun getAllStations() : Observable<List<StationModel>>
}