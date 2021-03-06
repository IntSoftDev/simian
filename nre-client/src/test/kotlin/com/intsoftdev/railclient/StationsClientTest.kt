package com.intsoftdev.railclient

import com.intsoftdev.nreclient.domain.ReturnState
import com.intsoftdev.nreclient.domain.StationModel
import com.intsoftdev.nreclient.domain.StationsResult
import com.intsoftdev.nreclient.domain.Version
import com.intsoftdev.railclient.api.StationsClient
import com.intsoftdev.railclient.di.Di
import com.intsoftdev.nreclient.domain.interactor.GetStationsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module

class StationsClientTest : StationsKoinTest() {

    private val getStationsUseCase = mock<GetStationsUseCase>()

    private val testModule = module {
        single(override = true) { getStationsUseCase }
    }

    private lateinit var stationsClient: StationsClient

    @Before
    fun setup() {
        initialize()

        Di.koinApplication.modules(testModule)

        stationsClient = StationsClient()
    }

    @Test
    fun `given use case success then sdk returns expected`() {
        // Given
        val result = Observable.just(ReturnState.Success(
            StationsResult(
                version = Version(version = 0.3, lastUpdated = 1L),
                stations = emptyList<StationModel>()
            )
        ))

        whenever(getStationsUseCase.getAllStations()).thenAnswer {
            return@thenAnswer result
        }

        // When
        val stationsResult = stationsClient.getAllStations()

        // Then
        Assert.assertEquals(stationsResult, result)
    }

    @Test(expected = Exception::class)
    fun `given use case error then sdk throws exception`() {
        // Given
        whenever(getStationsUseCase.getAllStations()).thenThrow(Exception(""))

        // When
        stationsClient.getAllStations()
    }
}