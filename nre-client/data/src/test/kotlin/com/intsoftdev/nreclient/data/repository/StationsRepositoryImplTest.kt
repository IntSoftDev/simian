package com.intsoftdev.nreclient.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intsoftdev.nreclient.data.RxImmediateSchedulerRule
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStoreImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStoreImpl
import com.intsoftdev.nreclient.domain.StationModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StationsRepositoryImplTest {

    private val stationsCacheStore = mock<StationsCacheDataStoreImpl>()
    private val stationsRemoteStore = mock<StationsRemoteDataStoreImpl>()
    private val stationMapper = mock<StationModelMapper>()

    private lateinit var cut : StationsRepositoryImpl

    @Rule
    @JvmField
    val schedulersRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        cut = StationsRepositoryImpl(stationsCacheStore, stationsRemoteStore, stationMapper)
    }

    @Test
    fun `given cache and not expired when get stations then cache is used`() {
        // given
        val dummyList = emptyList<StationEntity>()
        whenever(stationsCacheStore.isCached()).thenReturn(Single.just(true))
        whenever(stationsCacheStore.isCacheExpired()).thenReturn(false)
        whenever(stationsCacheStore.getAllStationsFromCache()).thenReturn(Observable.just(dummyList))
        // when
        cut.getAllStations().test()
        // then
        verify(stationsCacheStore).getAllStationsFromCache()
        verify(stationsRemoteStore, never()).getAllStationsFromServer()
        verify(stationsCacheStore, never()).saveAllStationsToCache(dummyList)
    }

    @Test
    fun `given cache and expired when get stations then cache is not used and remote is used`() {
        // given
        val dummyList = emptyList<StationEntity>()
        whenever(stationsCacheStore.isCached()).thenReturn(Single.just(true))
        whenever(stationsCacheStore.isCacheExpired()).thenReturn(true)
        whenever(stationsRemoteStore.getAllStationsFromServer()).thenReturn(Observable.just(dummyList))
        // when
        cut.getAllStations().test()
        // then
        verify(stationsRemoteStore).getAllStationsFromServer()
        verify(stationsCacheStore, never()).getAllStationsFromCache()
    }

    @Test
    fun `when save stations called then stations saved to cache`() {
        // given
        val dummyList = emptyList<StationModel>()
        whenever(stationsCacheStore.saveAllStationsToCache(any()))
                .thenReturn(Completable.complete())
        // when
        cut.saveAllStations(dummyList).test()
        // then
        verify(stationsCacheStore).saveAllStationsToCache(any())
    }
}