package com.intsoftdev.nreclient.data.di

import android.content.Context
import androidx.room.Room
import com.intsoftdev.nreclient.data.*
import com.intsoftdev.nreclient.data.db.StationConstants
import com.intsoftdev.nreclient.data.db.StationsDatabase
import com.intsoftdev.nreclient.data.mapper.StationEntityMapper
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.repository.cache.StationsCache
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepository
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepositoryImpl
import com.intsoftdev.nreclient.domain.StationsDataRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8080/"
private const val DEFAULT_TIMEOUT = 15L
private const val CACHE_SIZE_BYTES = 1024 * 1024 * 2L

val dbModule = module {

    single {
        Room.databaseBuilder(
                androidApplication(),
                StationsDatabase::class.java,
                StationConstants.TABLE_NAME).build()
    }

    factory { get<StationsDatabase>().cachedStationDao() }

    factory { StationEntityMapper() }

    factory { StationModelMapper() }

    factory { PreferencesHelper(androidContext()) }

    factory<StationsCache> {
        StationsCacheImpl(
                stationsDatabase = get(),
                entityMapper = get(),
                preferencesHelper = get())
    }
}

val dataModule = module {

    factory<StationsRemoteRepository> {
        StationsRemoteRepositoryImpl(
                stationsProxyService = get(),
                stationMapper = get())
    }

    factory<StationsDataRepository> {
        StationsDataRepositoryImpl(
                factory = get(),
                stationMapper = get())
    }

    factory {
        StationsRemoteDataStore(stationsRemoteRepository = get())
    }

    factory { StationsCacheDataStore(stationsCache = get()) }

    factory {
        StationsDataStoreFactory(
                stationsCache = get(),
                stationsCacheDataStore = get(),
                stationsRemoteDataStore = get())
    }

    factory<StationsProxyService> {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(StationsProxyService::class.java)
    }

    factory {
        OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(Cache(get<Context>().cacheDir, CACHE_SIZE_BYTES))
                .addInterceptor(StatusCodeInterceptor())
                .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG)
                                HttpLoggingInterceptor.Level.BODY
                            else
                                HttpLoggingInterceptor.Level.NONE
                        })
                .build()
    }
}