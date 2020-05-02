package com.intsoftdev.nreclient.data.repository.remote

import com.intsoftdev.nreclient.data.model.StationEntity
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
        stubStationsRemoteGetStations(Observable.just(makeStationEntityList(2)))
        // when
        val testObserver = cut.getAllStationsFromServer().test()
        // then
        testObserver.assertComplete()
    }

    private fun stubStationsRemoteGetStations(single: Observable<List<StationEntity>>) {
        whenever(stationsRemoteRepository.getAllStations())
                .thenReturn(single)
    }

    private fun makeStationEntityList(count: Int): List<StationEntity> {
        val stationEntities = mutableListOf<StationEntity>()
        repeat(count) {
            stationEntities.add(makeStationEntity())
        }
        return stationEntities
    }

    private fun makeStationEntity(): StationEntity {
        return StationEntity("WTN", "Whitton", 52.4, 0.03)
    }
}