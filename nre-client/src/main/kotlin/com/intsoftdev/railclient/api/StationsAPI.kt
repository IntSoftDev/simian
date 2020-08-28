package com.intsoftdev.railclient.api

import android.location.Location
import com.intsoftdev.nreclient.domain.ReturnState
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsResult
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable

interface StationsAPI {
    fun getAllStations() : Observable<ReturnState<StationsResult>>
    fun getLondonStations() : Observable<List<StationModel>>
    fun getStationsByLocation(location: Location, numResults: Int)

    fun getModelFromName(stationName: String) : Observable<StationModel>
    fun getModelFromCode(crsCode: String) : Observable<StationModel>
}