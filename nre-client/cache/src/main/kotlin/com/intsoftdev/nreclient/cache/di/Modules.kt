package com.intsoftdev.nreclient.cache.di

import androidx.room.Room
import com.intsoftdev.nreclient.cache.StationsCacheImpl
import com.intsoftdev.nreclient.cache.db.StationsDatabase
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val cacheModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            StationsDatabase::class.java,
            "nre_stations.db"
        ).build()
    }

    factory { get<StationsDatabase>().cachedStationDao() }

    factory<StationsCache> {
        StationsCacheImpl(
            stationsDatabase = get()
        )
    }
}

