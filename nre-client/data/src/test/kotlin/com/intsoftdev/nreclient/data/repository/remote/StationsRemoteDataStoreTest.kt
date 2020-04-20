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

    private lateinit var stationsRemoteDataStore: StationsRemoteDataStore

    private lateinit var stationsRemoteRepository: StationsRemoteRepository

    @Before
    fun setUp() {
        stationsRemoteRepository = mock()
        stationsRemoteDataStore = StationsRemoteDataStore(stationsRemoteRepository)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearStationsThrowsException() {
        stationsRemoteDataStore.clearStations().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveStationsThrowsException() {
        stationsRemoteDataStore.saveAllStations(makeStationEntityList(2)).test()
    }

    @Test
    fun getStationsCompletes() {
        stubStationsCacheGetStations(Observable.just(makeStationEntityList(2)))
        val testObserver = stationsRemoteRepository.getAllStations().test()
        testObserver.assertComplete()
    }

    private fun stubStationsCacheGetStations(single: Observable<List<StationEntity>>) {
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