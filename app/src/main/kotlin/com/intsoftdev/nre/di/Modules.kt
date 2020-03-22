package com.intsoftdev.nre.di

import com.intsoftdev.railclient.presentation.StationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        StationsViewModel()
    }
}
