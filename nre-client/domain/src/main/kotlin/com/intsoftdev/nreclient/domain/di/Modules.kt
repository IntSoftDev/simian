package com.intsoftdev.nreclient.domain.di

import com.intsoftdev.nreclient.domain.interactor.GetStationsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetStationsUseCase(stationsRepository = get()) }
}