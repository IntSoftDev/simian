package com.intsoftdev.nreclient.domain

import io.reactivex.Observable

interface StationsRepository {
    fun getAllStations() : Observable<List<StationModel>>
}