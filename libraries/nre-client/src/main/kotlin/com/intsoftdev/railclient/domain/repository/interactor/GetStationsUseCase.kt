package com.intsoftdev.railclient.domain.repository.interactor

import com.intsoftdev.railclient.data.StationModel
import com.intsoftdev.railclient.domain.repository.StationsRepository
import io.reactivex.Observable

class GetStationsUseCase(private val stationsRepository: StationsRepository) {
    fun getAllStations(): Observable<List<StationModel>> = stationsRepository.getAllStations()
}