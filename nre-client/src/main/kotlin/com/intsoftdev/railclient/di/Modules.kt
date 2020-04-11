package com.intsoftdev.railclient.di

import com.intsoftdev.railclient.api.StationsClient
import org.koin.dsl.module

val sdkModule = module {
    factory { StationsClient() }
}