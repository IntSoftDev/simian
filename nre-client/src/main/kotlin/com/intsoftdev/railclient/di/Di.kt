package com.intsoftdev.railclient.di

import android.content.Context
import com.intsoftdev.nreclient.cache.di.cacheModule
import com.intsoftdev.nreclient.data.di.dataModule
import com.intsoftdev.nreclient.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

internal object Di {

    lateinit var koinApplication: KoinApplication

    private val modules = listOf(
            domainModule,
            dataModule,
            cacheModule
    )

    fun init(context: Context) {
        koinApplication = koinApplication {
            modules(modules)
            androidContext(context.applicationContext)
        }
    }

    fun koin() = koinApplication.koin
}
