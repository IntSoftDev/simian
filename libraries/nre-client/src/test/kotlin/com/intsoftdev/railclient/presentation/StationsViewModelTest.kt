package com.intsoftdev.railsdk.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.intsoftdev.railsdk.NreKoinTest
import com.intsoftdev.railsdk.RxImmediateSchedulerRule
import com.intsoftdev.railsdk.data.StationModel
import com.intsoftdev.railsdk.di.Di
import com.intsoftdev.railsdk.domain.repository.interactor.GetStationsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class StationsViewModelTest : NreKoinTest() {

    @Rule
    @JvmField
    val schedulersRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getStationsUseCase: GetStationsUseCase

    private val cut: StationsViewModel by lazy { StationsViewModel() }

    @Before
    fun setup() {
        initialize()
        provideMocks()
    }

    @Test
    fun `given use case success then live data is returned and error state false`(){
        // Given
        val dummyList = emptyList<StationModel>()
        whenever(getStationsUseCase.getAllStations()).thenReturn(Observable.just(dummyList))

        // When
        cut.getAllStations()

        // Then
        assertEquals(cut.stationLiveData.value, dummyList)
        assertEquals(cut.errorStateLiveData.value, false)
    }

    @Test
    fun `given use case error then error state true`(){
        // Given
        val mockError = mock<Throwable>()
        val dummyList = emptyList<StationModel>()
        whenever(getStationsUseCase.getAllStations()).thenReturn(Observable.error(mockError))

        // When
        cut.getAllStations()

        // Then
        assertEquals(cut.errorStateLiveData.value, true)
    }

    private fun provideMocks() {
        val testModule = module {
            factory(override = true) {
                getStationsUseCase
            }
        }
        Di.koinApplication.modules(testModule)
    }
}