package com.intsoftdev.nreclient.data.di

import android.content.Context
import com.intsoftdev.nreclient.data.*
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.network.StationsProxyService
import com.intsoftdev.nreclient.data.network.StatusCodeInterceptor
import com.intsoftdev.nreclient.data.repository.StationsDataStoreFactory
import com.intsoftdev.nreclient.data.repository.StationsRepositoryImpl
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepository
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepositoryImpl
import com.intsoftdev.nreclient.domain.StationsRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8080/"
private const val DEFAULT_TIMEOUT = 15L
private const val CACHE_SIZE_BYTES = 1024 * 1024 * 2L

val dataModule = module {

    factory { StationModelMapper() }

    factory<StationsRemoteRepository> {
        StationsRemoteRepositoryImpl(
                stationsProxyService = get(),
                stationMapper = get())
    }

    factory<StationsRepository> {
        StationsRepositoryImpl(
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