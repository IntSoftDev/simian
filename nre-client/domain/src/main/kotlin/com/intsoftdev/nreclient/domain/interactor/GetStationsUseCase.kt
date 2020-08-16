package com.intsoftdev.nreclient.domain.interactor

import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsRepository
import com.intsoftdev.nreclient.domain.StationsResult
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable

class GetStationsUseCase(private val stationsRepository: StationsRepository) {
    fun getAllStations(): Observable<StationsResult> {
        return stationsRepository.getAllStations()
    }

    fun getStationFromCrs(crs: String): Observable<StationModel> =
        stationsRepository.getModelFromCache(null, crs)
}