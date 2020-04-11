package com.intsoftdev.nreclient.data

import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsRepository
import io.reactivex.Observable

class StationsRepositoryImpl(
        private val stationsProxyService: StationsProxyService
) : StationsRepository {
    override fun getAllStations(): Observable<List<StationModel>> = stationsProxyService.getAllStations()
}