package com.intsoftdev.railclient.domain.repository.interactor

import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsDataRepository
import io.reactivex.Observable

class GetStationsUseCase(private val stationsDataRepository: StationsDataRepository) {
    fun getAllStations(): Observable<List<StationModel>> = stationsDataRepository.getAllStations()
}