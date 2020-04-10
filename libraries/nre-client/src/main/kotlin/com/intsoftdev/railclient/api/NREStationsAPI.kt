package com.intsoftdev.railclient.api

import android.location.Location
import com.intsoftdev.railclient.data.StationModel
import io.reactivex.Observable

interface NREStationsAPI {
    fun getAllStations() : Observable<List<StationModel>>
    fun getLondonStations() : Observable<List<StationModel>>
    fun getStationsByLocation(location: Location, numResults: Int)
}