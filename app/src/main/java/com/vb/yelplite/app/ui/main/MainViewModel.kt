package com.vb.yelplite.app.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.vb.yelplite.app.domain.Business
import com.vb.yelplite.app.domain.FindNearbyBusinesses
import com.vb.yelplite.app.domain.LocationProviderDisabledException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class MainAction {
    NOTHING,
    ERROR_LOCATION_PROVIDER,
    ERROR_INTERNET
}

class MainViewModel(
    private val findNearbyBusinesses: FindNearbyBusinesses
) : ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchBusinesses() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    businesses.postValue(findNearbyBusinesses.run())
                }
                state.postValue(MainAction.NOTHING)
            } catch (e: Exception) {
                Log.i("VB", "Exception on findNearbyBusinesses", e)
                state.postValue(if (e is LocationProviderDisabledException) MainAction.ERROR_LOCATION_PROVIDER else MainAction.ERROR_INTERNET)
            }
        }
    }

    val businesses = MutableLiveData<List<Business>>()

    val selectedBusinessId = MutableLiveData<String>()

    val state = MutableLiveData<MainAction>(MainAction.NOTHING)

    fun seeBusinessDetails(business: Business) {
        selectedBusinessId.value = business.id
    }
}