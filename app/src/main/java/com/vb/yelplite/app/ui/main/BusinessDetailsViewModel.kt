package com.vb.yelplite.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vb.yelplite.app.data.BusinessRepository
import com.vb.yelplite.app.domain.BusinessDetails
import java.lang.Exception

class BusinessDetailsViewModel(
    val id: String,
    val businessRepository: BusinessRepository
) : ViewModel() {

    val businessDetails: LiveData<BusinessDetails> = liveData {
        try {
            val data = businessRepository.getBusinessDetail(id)
            emit(data)
        } catch (e: Exception) {
    // error state
        }
    }
}