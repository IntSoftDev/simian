package com.intsoftdev.railsdk.data

import io.reactivex.Observable
import retrofit2.http.GET

interface NREProxyApi {

    @GET("nre")
    fun getAllStations(): Observable<List<StationModel>>
}