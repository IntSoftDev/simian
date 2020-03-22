package com.intsoftdev.railclient.data

import com.intsoftdev.railclient.domain.repository.StationsRepository
import io.reactivex.Observable

class StationsRepositoryImpl(
        private val nreProxyApi: NREProxyApi
) : StationsRepository {
    override fun getAllStations(): Observable<List<StationModel>> = nreProxyApi.getAllStations()
}