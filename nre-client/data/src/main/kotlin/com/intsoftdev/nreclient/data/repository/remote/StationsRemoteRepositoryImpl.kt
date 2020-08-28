package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.network.StationsProxyService
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version
import io.reactivex.Observable

/**
 * Remote implementation for retrieving Stations instances
 */
internal class StationsRemoteRepositoryImpl(
    private val stationsProxyService: StationsProxyService
) : StationsRemoteRepository {

    override fun getAllStations(): Observable<List<StationModel>> {
        return stationsProxyService.getAllStations()
    }

    override fun getStationsVersion(): Observable<List<Version>> {
        return stationsProxyService.getStationsVersion()
    }
}