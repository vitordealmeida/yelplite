package com.vb.yelplite.app.data

import android.util.Log
import com.vb.yelplite.app.domain.Business
import org.koin.dsl.module
import java.lang.Exception

val dataModule = module {
    factory { BusinessRepository(get()) }
}

class BusinessRepository(private val api : YelpApi) {

    suspend fun searchBusinesses(latitude: Double, longitude: Double) : List<RemoteBusiness>?{
        return try {
            api.searchBusinesses(latitude, longitude).businesses

        } catch (e: Exception) {
            Log.e("VB", "Error on api call", e)
            emptyList()
        }
    }
}