package com.intsoftdev.railclient.domain.repository.interactor

import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsRepository
import io.reactivex.Observable

class GetStationsUseCase(private val stationsRepository: StationsRepository) {
    fun getAllStations(): Observable<List<StationModel>> = stationsRepository.getAllStations()
    fun getStationFromCrs(crs: String):Observable<StationModel> = stationsRepository.getModelFromCache(null, crs)
}