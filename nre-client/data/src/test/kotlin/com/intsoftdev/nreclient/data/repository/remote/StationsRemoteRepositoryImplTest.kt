package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.network.StationsProxyService
import com.intsoftdev.nreclient.domain.StationModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StationsRemoteRepositoryImplTest {

    private lateinit var stationsService: StationsProxyService

    private lateinit var stationsRemoteRepository: StationsRemoteRepositoryImpl

   @Before
    fun setup() {
        stationsService = mock()
        stationsRemoteRepository = StationsRemoteRepositoryImpl(stationsService)
    }

    @Test
    fun getStationsReturnsData() {
        val stationsResponse = makeStationsResponse()
        stubStationServiceGetStations(Observable.just(stationsResponse))
        val testObserver = stationsRemoteRepository.getAllStations().test()
        testObserver.assertValue(stationsResponse)
    }

    private fun stubStationServiceGetStations(observable:
                                              Observable<List<StationModel>>) {
        whenever(stationsService.getAllStations())
                .thenReturn(observable)
    }

    fun makeStationsResponse(): List<StationModel> {
        return makeStationModelList(5)
    }

    fun makeStationModelList(count: Int): List<StationModel> {
        val stationModels = mutableListOf<StationModel>()
        repeat(count) {
            stationModels.add(makeStationModel())
        }
        return stationModels
    }

    fun makeStationModel(): StationModel {
        return StationModel("name", "crs", 0.0, 0.0)
    }
}