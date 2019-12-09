package com.vb.yelplite.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vb.yelplite.app.data.BusinessRepository

class BusinessDetailsViewModel(
    val id: String,
    val businessRepository: BusinessRepository
) : ViewModel() {

    val businessDetails = liveData {
        try {
            val data = businessRepository.getBusinessDetail(id)
            emit(data)
        } catch (e: Exception) {
            // error state
        }
    }

    val businessReviews = liveData {
        try {
            val data = businessRepository.getBusinessReviews(id).reviews
            emit(data)
        } catch (e: Exception) {
            // error state
        }
    }
}