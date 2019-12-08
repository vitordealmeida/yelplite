package com.vb.yelplite.app.domain

import org.koin.dsl.module

val domainModule = module {
    factory { GetCurrentLocation(get(), get()) }
    factory { FindNearbyBusinesses(get(), get()) }
}