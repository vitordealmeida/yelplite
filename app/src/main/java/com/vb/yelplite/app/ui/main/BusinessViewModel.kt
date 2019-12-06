package com.vb.yelplite.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vb.yelplite.app.data.BusinessRepository
import com.vb.yelplite.app.data.RemoteBusiness
import com.vb.yelplite.app.data.YelpApifactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BusinessViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository: BusinessRepository = BusinessRepository(YelpApifactory.yelpApi)

    val businessesLiveData = MutableLiveData<MutableList<RemoteBusiness>>()

    fun fetchBusinesses() {
        scope.launch {
            val popularMovies = repository.searchBusinesses()
            businessesLiveData.postValue(popularMovies)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}