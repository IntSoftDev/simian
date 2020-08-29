package com.intsoftdev.nre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intsoftdev.nreclient.domain.*
import com.intsoftdev.railclient.api.StationsClient
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class StationsViewModelTest {

    @Rule
    @JvmField
    val schedulersRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val stationsClient = mock<StationsClient>()

    private val cut: StationsViewModel by lazy { StationsViewModel(stationsClient) }

    @Test
    fun `given stations api success then live data is returned and error state false`() {
        // Given

        val success = ReturnState.Success(
            StationsResult(
                version = Version(version = 0.3, lastUpdated = 1L),
                stations = emptyList<StationModel>()
            )
        )

        val result = Observable.just(success)

        whenever(stationsClient.getAllStations()).thenAnswer {
            return@thenAnswer result
        }

        // When
        cut.getAllStations()

        // Then
        assertEquals(cut.result.value, success.data)
    }
}