package com.intsoftdev.railclient.data

import io.reactivex.Observable
import retrofit2.http.GET

interface NREProxyApi {

    @GET("crslocations")
    fun getAllStations(): Observable<List<StationModel>>
}