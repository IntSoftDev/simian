package com.intsoftdev.nreclient.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intsoftdev.nreclient.data.RxImmediateSchedulerRule
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.mapper.VersionModelMapper
import com.intsoftdev.nreclient.data.model.StationEntity
import com.intsoftdev.nreclient.data.model.VersionEntity
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStoreImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStoreImpl
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.Version
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
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
    private val versionMapper = mock<VersionModelMapper>()
    private val dataUpdateResolver = mock<DataUpdateResolver>()

    private lateinit var cut: StationsRepositoryImpl

    @Rule
    @JvmField
    val schedulersRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        cut = StationsRepositoryImpl(
            stationsCacheStore,
            stationsRemoteStore,
            stationMapper,
            versionMapper,
            dataUpdateResolver
        )
    }

    @Test
    fun `given data update local then cached stations returned`() {
        // given
        val dummyList = emptyList<StationEntity>()
        val dummyVersion = mock<Version>()
        //  val result = StationsResult(dummyVersion, dummyList)
        whenever(dataUpdateResolver.getUpdateAction(stationsCacheStore)).thenReturn(
            Observable.just(
                DataUpdateAction.LOCAL
            )
        )
        whenever(stationsCacheStore.getVersionFromCache()).thenReturn(Observable.just(mock()))
        whenever(stationsCacheStore.getAllStationsFromCache()).thenReturn(Observable.just(dummyList))
        // when
        cut.getAllStations().test()
        // then
        verify(stationsCacheStore).getAllStationsFromCache()
        verify(stationsRemoteStore, never()).getAllStationsFromServer()
    }

    @Test
    fun `given data update refresh when new version then stations updated`() {
        // given
        val cachedVersion = VersionEntity(version = 0.2, lastUpdated = 0L)
        val serverVersion = listOf(Version(0.3, 1L))
        val dummyList = emptyList<StationModel>()

        whenever(dataUpdateResolver.getUpdateAction(stationsCacheStore)).thenReturn(
            Observable.just(
                DataUpdateAction.REFRESH
            )
        )
        whenever(stationsCacheStore.getVersionFromCache()).thenReturn(Observable.just(cachedVersion))
        whenever(stationsRemoteStore.getStationsVersion()).thenReturn(Observable.just(serverVersion))
        whenever(stationsRemoteStore.getAllStationsFromServer()).thenReturn(
            Observable.just(
                dummyList
            )
        )
        // when
        cut.getAllStations().test()
        // then
        verify(stationsCacheStore).getVersionFromCache()
        verify(stationsRemoteStore).getAllStationsFromServer()
    }

    @Test
    fun `given data update refresh when not new version then stations from cache`() {
        // given
        val cachedVersion = VersionEntity(version = 0.3, lastUpdated = 0L)
        val listVersions = listOf(Version(0.3, 1L))
        val dummyList = emptyList<StationModel>()

        whenever(dataUpdateResolver.getUpdateAction(stationsCacheStore)).thenReturn(
            Observable.just(
                DataUpdateAction.REFRESH
            )
        )
        whenever(stationsCacheStore.getVersionFromCache()).thenReturn(Observable.just(cachedVersion))
        whenever(stationsRemoteStore.getStationsVersion()).thenReturn(Observable.just(listVersions))
        whenever(stationsRemoteStore.getAllStationsFromServer()).thenReturn(
            Observable.just(
                dummyList
            )
        )
        // when
        cut.getAllStations().test()
        // then
        verify(stationsCacheStore).getVersionFromCache()
        verify(stationsCacheStore).getAllStationsFromCache()
        verify(stationsRemoteStore, never()).getAllStationsFromServer()
    }

    @Test
    fun `given data update remote then server stations returned`() {
        // given
        val dummyList = emptyList<StationModel>()
        val listVersions = listOf(Version(0.2, 1L))
        whenever(dataUpdateResolver.getUpdateAction(stationsCacheStore)).thenReturn(
            Observable.just(
                DataUpdateAction.REMOTE
            )
        )
        whenever(stationsRemoteStore.getAllStationsFromServer()).thenReturn(
            Observable.just(
                dummyList
            )
        )
        whenever(stationsRemoteStore.getStationsVersion()).thenReturn(Observable.just(listVersions))

        // when
        cut.getAllStations().test()
        // then
        verify(stationsRemoteStore).getStationsVersion()
        verify(stationsRemoteStore).getAllStationsFromServer()
        verify(stationsCacheStore, never()).getAllStationsFromCache()
    }

    @Test
    fun `when save stations called then stations saved to cache`() {
        // given
        val listVersions = listOf(Version(0.2, 1L))
        val dummyList = emptyList<StationModel>()

        whenever(versionMapper.mapToEntity(any())).thenReturn(mock())
        whenever(stationsCacheStore.saveAllStationsToCache(any(), any()))
            .thenReturn(Completable.complete())
        // when
        cut.saveAllStations(listVersions.first(), dummyList).test()
        // then
        verify(stationsCacheStore).saveAllStationsToCache(any(), any())
    }
}