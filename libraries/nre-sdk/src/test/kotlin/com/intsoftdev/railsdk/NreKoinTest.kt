package com.intsoftdev.railsdk

import android.content.Context
import com.intsoftdev.railsdk.di.Di
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.After
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class NreKoinTest : KoinTest {
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