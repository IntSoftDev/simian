package com.intsoftdev.railclient.di

import org.koin.core.Koin
import org.koin.core.KoinComponent

internal interface DIComponent : KoinComponent {
    override fun getKoin(): Koin {
        return Di.koin()
    }
}
