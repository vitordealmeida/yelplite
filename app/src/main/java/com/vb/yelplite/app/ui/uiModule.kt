package com.vb.yelplite.app.ui

import com.vb.yelplite.app.ui.main.MainFragment
import com.vb.yelplite.app.ui.main.MainViewModel
import com.vb.yelplite.app.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val uiModule = module {
    factory { MainFragment() }
    viewModel { MainViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}