package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.domain.StationModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StationsRemoteDataStoreTest {

    private var stationsRemoteRepository = mock<StationsRemoteRepository>()
    private var cut = StationsRemoteDataStoreImpl(stationsRemoteRepository)

    @Test
    fun `given remote stations when observed then completes`() {
        // given
        stubStationsRemoteGetStations(Observable.just(makeStationModelList(2)))
        // when
        val testObserver = cut.getAllStationsFromServer().test()
        // then
        testObserver.assertComplete()
    }

    private fun stubStationsRemoteGetStations(observable: Observable<List<StationModel>>) {
        whenever(stationsRemoteRepository.getAllStations())
                .thenReturn(observable)
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