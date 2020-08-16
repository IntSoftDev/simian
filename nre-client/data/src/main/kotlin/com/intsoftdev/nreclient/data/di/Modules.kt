package com.intsoftdev.nreclient.data.di

import com.intsoftdev.nreclient.data.BuildConfig
import com.intsoftdev.nreclient.data.PreferencesHelper
import com.intsoftdev.nreclient.data.mapper.StationModelMapper
import com.intsoftdev.nreclient.data.mapper.VersionModelMapper
import com.intsoftdev.nreclient.data.network.StationsProxyService
import com.intsoftdev.nreclient.data.network.StatusCodeInterceptor
import com.intsoftdev.nreclient.data.repository.DataUpdateResolver
import com.intsoftdev.nreclient.data.repository.StationsRepositoryImpl
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStore
import com.intsoftdev.nreclient.data.repository.cache.StationsCacheDataStoreImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStore
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteDataStoreImpl
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepository
import com.intsoftdev.nreclient.data.repository.remote.StationsRemoteRepositoryImpl
import com.intsoftdev.nreclient.domain.StationsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://onrails.azurewebsites.net/"
private const val DEFAULT_TIMEOUT = 30L
private const val CACHE_SIZE_BYTES = 1024 * 1024 * 2L

val dataModule = module {

    factory { StationModelMapper() }

    factory { VersionModelMapper() }

    factory<StationsRemoteRepository> {
        StationsRemoteRepositoryImpl(
            stationsProxyService = get()
        )
    }

    factory<StationsRepository> {
        StationsRepositoryImpl(
            stationsCacheStore = get(),
            stationsRemoteStore = get(),
            stationMapper = get(),
            versionMapper = get(),
            dataUpdateResolver = get()
        )
    }

    factory { PreferencesHelper(androidContext()) }

    factory<DataUpdateResolver> {
        DataUpdateResolver(get())
    }

    factory<StationsRemoteDataStore> {
        StationsRemoteDataStoreImpl(stationsRemoteRepository = get())
    }

    factory<StationsCacheDataStore> { StationsCacheDataStoreImpl(stationsCache = get()) }

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
            .cache(null)
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