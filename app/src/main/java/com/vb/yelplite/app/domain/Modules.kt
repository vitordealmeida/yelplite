package com.vb.yelplite.app.domain

import com.vb.yelplite.app.data.BusinessRepository
import org.koin.dsl.module

val domainModule = module {
    factory { GetCurrentLocation(get(), get()) }
    factory { FindNearbyBusinesses(get(), get()) }
}