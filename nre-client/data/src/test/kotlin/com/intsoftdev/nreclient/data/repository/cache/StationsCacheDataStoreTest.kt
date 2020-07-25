package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StationsCacheDataStoreTest {

    private val stationsCache = mock<StationsCache>()
    private val cut = StationsCacheDataStoreImpl(stationsCache)

    @Test
    fun `when cache has stations then stations are returned`() {
        // when
        whenever(stationsCache.getStations()).thenReturn(Observable.just(mutableListOf<StationEntity>()))
        val testObserver = cut.getAllStationsFromCache().test()
        // then
        testObserver.assertComplete()
        verify(stationsCache).getStations()
    }

    @Test
    fun `when station entities are saved then cache and time are updated`() {
        // given
        val stationEntities = mutableListOf<StationEntity>()
        // when
        whenever(stationsCache.saveStations(stationEntities)).thenReturn(Completable.complete())
        val testObserver= cut.saveAllStationsToCache(stationEntities).test()
        // then
        testObserver.assertComplete()
        verify(stationsCache).saveStations(stationEntities)
        verify(stationsCache).setLastCacheTime(any())
    }

    @Test
    fun `when cached stations are cleared then cache is cleared`() {
        // when
        cut.clearCachedStations()
        // then
        verify(stationsCache).clearAll()
    }

    @Test
    fun `when is cache called then cache is checked`() {
        // when
        cut.isCached()
        // then
        verify(stationsCache).isDataCached()
    }
}