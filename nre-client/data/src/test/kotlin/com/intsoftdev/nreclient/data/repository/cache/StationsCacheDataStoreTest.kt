package com.intsoftdev.nreclient.data.repository.cache

import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
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
    fun `when cache has version then version is returned`() {
        // when
        whenever(stationsCache.getVersionDetails()).thenReturn(Observable.just(mock()))
        val testObserver = cut.getVersionDetails().test()
        // then
        testObserver.assertComplete()
        verify(stationsCache).getVersionDetails()
    }

    @Test
    fun `when station entities are saved then cache and time are updated`() {
        // given
        val stationEntities = mutableListOf<StationEntity>()
        val versionEntity = VersionEntity(version = 0.3, lastUpdated = 1L)
        // when
        whenever(stationsCache.saveStations(versionEntity, stationEntities)).thenReturn(Completable.complete())
        val testObserver= cut.saveAllStationsToCache(versionEntity, stationEntities).test()
        // then
        testObserver.assertComplete()
        verify(stationsCache).saveStations(versionEntity, stationEntities)
    }

    @Test
    fun `when cached stations are cleared then cache is cleared`() {
        // when
        cut.clearCachedStations()
        // then
        verify(stationsCache).clearAll()
    }

    @Test
    fun `when is empty called then cache empty checked`() {
        // when
        cut.isEmpty()
        // then
        verify(stationsCache).isCacheEmpty()
    }
}