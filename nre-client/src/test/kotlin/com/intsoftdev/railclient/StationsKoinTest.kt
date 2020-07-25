package com.intsoftdev.railclient

import android.content.Context
import com.intsoftdev.railclient.di.Di
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class StationsKoinTest : KoinTest {
    @Mock
    protected lateinit var context: Context

    protected fun initialize() {
        MockitoAnnotations.initMocks(this)
        whenever(context.applicationContext).thenReturn(context)
        Di.init(context)
    }

    @After
    fun cleanup() {
        Di.koin().close()
    }
}