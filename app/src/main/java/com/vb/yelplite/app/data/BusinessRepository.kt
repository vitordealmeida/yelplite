package com.vb.yelplite.app.data

class BusinessRepository(private val api : YelpApi) : BaseRepository() {

    suspend fun searchBusinesses() : MutableList<RemoteBusiness>?{

        val movieResponse = safeApiCall(
            call = {api.searchBusinesses().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return movieResponse?.businesses?.toMutableList()
    }
}