package com.intsoftdev.railsdk.data

import com.intsoftdev.railsdk.domain.repository.StationsRepository
import com.intsoftdev.railsdk.presentation.StationsViewModel
import io.reactivex.Observable

class StationsRepositoryImpl(
        private val nreProxyApi: NREProxyApi
) : StationsRepository {
    override fun getAllStations(): Observable<List<StationModel>> = nreProxyApi.getAllStations()
}