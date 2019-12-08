package com.vb.yelplite.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vb.yelplite.app.domain.Business
import com.vb.yelplite.app.domain.FindNearbyBusinesses

enum class MainAction {
    NOTHING,
    ERROR_LOCATION_PROVIDER,
    ERROR_INTERNET
}

class MainViewModel(
    val findNearbyBusinesses: FindNearbyBusinesses
) : ViewModel() {

    val businesses: LiveData<List<Business>> = liveData {
        val data = findNearbyBusinesses.run()
        emit(data)
    }

    val selectedBusinessId = MutableLiveData<String>()

    val state = MutableLiveData<MainAction>(MainAction.NOTHING)

    fun seeBusinessDetails(business: Business) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val businessDetails = CoroutineScope(Dispatchers.IO).async {
//                businessRepository.getBusinessDetail(business.id)
//            }.await()
//            selectedBusiness.value = (businessDetails)
//        }
        selectedBusinessId.value = business.id
    }
}