package com.intsoftdev.nreclient.domain.di

import com.intsoftdev.railclient.domain.repository.interactor.GetStationsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetStationsUseCase(stationsRepository = get()) }
    //  factory { NREStationsSDK() }
}