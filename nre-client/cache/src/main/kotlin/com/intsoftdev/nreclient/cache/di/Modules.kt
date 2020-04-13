package com.intsoftdev.nreclient.cache.di

import androidx.room.Room
import com.intsoftdev.nreclient.cache.PreferencesHelper
import com.intsoftdev.nreclient.cache.StationsCacheImpl
import com.intsoftdev.nreclient.cache.db.StationConstants
import com.intsoftdev.nreclient.cache.db.StationsDatabase
import com.intsoftdev.nreclient.cache.mapper.StationEntityMapper
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val BASE_URL = "http://10.0.2.2:8080/"
private const val DEFAULT_TIMEOUT = 15L
private const val CACHE_SIZE_BYTES = 1024 * 1024 * 2L

val cacheModule = module {

    single {
        Room.databaseBuilder(
                androidApplication(),
                StationsDatabase::class.java,
                StationConstants.TABLE_NAME).build()
    }

    factory { get<StationsDatabase>().cachedStationDao() }

    factory { StationEntityMapper() }

    factory { PreferencesHelper(androidContext()) }

    factory<StationsCache> {
        StationsCacheImpl(
                stationsDatabase = get(),
                entityMapper = get(),
                preferencesHelper = get())
    }
}

