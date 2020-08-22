package com.intsoftdev.nreclient.domain.interactor

import com.intsoftdev.nreclient.domain.*
import io.reactivex.Observable

class GetStationsUseCase(private val stationsRepository: StationsRepository) {
    fun getAllStations(): Observable<ReturnState<StationsResult>> {
        return stationsRepository.getAllStations().map<ReturnState<StationsResult>> {
            ReturnState.Success(it)
        }.onErrorReturn { throwable ->
            ReturnState.Error(throwable.toApiError())
        }.startWith(ReturnState.Loading())
    }

    fun getStationFromCrs(crs: String): Observable<StationModel> =
        stationsRepository.getModelFromCache(null, crs)
}