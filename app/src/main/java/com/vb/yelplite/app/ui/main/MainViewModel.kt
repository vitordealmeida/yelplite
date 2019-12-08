package com.vb.yelplite.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vb.yelplite.app.domain.Business
import com.vb.yelplite.app.domain.FindNearbyBusinesses
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

enum class MainAction {
    NOTHING,
    ERROR_LOCATION_PROVIDER,
    ERROR_INTERNET,
    FINISH
}

class MainViewModel(findNearbyBusinesses: FindNearbyBusinesses) : ViewModel() {

    val businesses: LiveData<List<Business>> = liveData {
        val data = findNearbyBusinesses.run()
        emit(data)
    }
}