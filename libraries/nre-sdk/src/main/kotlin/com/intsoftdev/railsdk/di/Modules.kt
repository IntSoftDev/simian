package com.intsoftdev.railsdk.di


import com.intsoftdev.railsdk.BuildConfig
import com.intsoftdev.railsdk.data.NREProxyApi
import com.intsoftdev.railsdk.data.StationsRepositoryImpl
import com.intsoftdev.railsdk.domain.repository.StationsRepository
import com.intsoftdev.railsdk.domain.repository.interactor.GetStationsUseCase
import com.intsoftdev.railsdk.presentation.StationsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8080/"
private const val DEFAULT_TIMEOUT = 15L

val domainModule = module {
    factory { GetStationsUseCase(stationsRepository = get()) }
}

val dataModule = module {

    factory<StationsRepository> { StationsRepositoryImpl(nreProxyApi = get()) }

    factory<NREProxyApi> {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(NREProxyApi::class.java)
    }

    factory {
        OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
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