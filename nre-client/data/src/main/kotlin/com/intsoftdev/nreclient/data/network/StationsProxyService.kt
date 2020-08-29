package com.intsoftdev.nreclient.data.network

import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the NREProxyAPI
 */
internal interface StationsProxyService {

    @GET("stations")
    fun getAllStations(): Observable<List<StationModel>>

    @GET("/config/stationsversion.json")
    fun getStationsVersion(): Observable<List<Version>>
}