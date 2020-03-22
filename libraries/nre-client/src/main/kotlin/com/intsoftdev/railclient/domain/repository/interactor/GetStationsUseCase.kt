package com.intsoftdev.railsdk.domain.repository.interactor

import com.intsoftdev.railsdk.data.StationModel
import com.intsoftdev.railsdk.domain.repository.StationsRepository
import io.reactivex.Observable

class GetStationsUseCase(private val stationsRepository: StationsRepository) {
    fun getAllStations(): Observable<List<StationModel>> = stationsRepository.getAllStations()
}