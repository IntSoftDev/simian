package com.intsoftdev.nreclient.data.di


import android.content.Context
import com.intsoftdev.nreclient.data.BuildConfig
import com.intsoftdev.nreclient.data.StationsProxyService
import com.intsoftdev.nreclient.data.StationsRepositoryImpl
import com.intsoftdev.nreclient.data.StatusCodeInterceptor
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

    factory<StationsRepository> { StationsRepositoryImpl(stationsProxyService = get()) }

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