package com.intsoftdev.nre.di

import com.intsoftdev.railsdk.presentation.StationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        StationsViewModel()
    }
}
