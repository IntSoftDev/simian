package com.intsoftdev.nre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intsoftdev.nreclient.domain.StationModel
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
        val dummyList = emptyList<StationModel>()
        whenever(stationsClient.getAllStations()).thenReturn(Observable.just(dummyList))

        // When
        cut.getAllStations()

        // Then
        assertEquals(cut.stationLiveData.value, dummyList)
        assertEquals(cut.errorStateLiveData.value, false)
    }

    @Test
    fun `given stations api error then error state true`() {
        // Given
        val mockError = mock<Throwable>()
        whenever(stationsClient.getAllStations()).thenReturn(Observable.error(mockError))

        // When
        cut.getAllStations()

        // Then
        assertEquals(cut.errorStateLiveData.value, true)
    }
}