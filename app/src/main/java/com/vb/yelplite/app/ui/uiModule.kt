package com.vb.yelplite.app.ui

import com.vb.yelplite.app.ui.main.BusinessDetailsViewModel
import com.vb.yelplite.app.ui.main.BusinessListFragment
import com.vb.yelplite.app.ui.main.MainViewModel
import com.vb.yelplite.app.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val uiModule = module {
    factory { BusinessListFragment() }
    viewModel { MainViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { (id: String) -> BusinessDetailsViewModel(id, get()) }
}