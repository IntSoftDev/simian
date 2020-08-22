package com.intsoftdev.railclient.api

import android.location.Location
import com.intsoftdev.nreclient.domain.ReturnState
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsResult
import com.intsoftdev.nreclient.domain.Version
import com.intsoftdev.railclient.di.DIComponent
import com.intsoftdev.nreclient.domain.interactor.GetStationsUseCase
import io.reactivex.Observable
import org.koin.core.get

class StationsClient : StationsAPI, DIComponent {

    private val getStationsUseCase by lazy { get<GetStationsUseCase>() }

    override fun getAllStations(): Observable<ReturnState<StationsResult>> =
        getStationsUseCase.getAllStations()

    override fun getLondonStations(): Observable<List<StationModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStationsByLocation(location: Location, numResults: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getModelFromName(stationName: String): Observable<StationModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getModelFromCode(crsCode: String): Observable<StationModel> {
        return getStationsUseCase.getStationFromCrs(crsCode)
    }
}