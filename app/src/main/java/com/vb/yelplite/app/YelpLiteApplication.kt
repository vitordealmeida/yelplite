package com.vb.yelplite.app

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vb.yelplite.app.data.BusinessRepository
import com.vb.yelplite.app.data.dataModule
import com.vb.yelplite.app.data.networkModule
import com.vb.yelplite.app.domain.domainModule
import com.vb.yelplite.app.ui.main.fragmentModule
import com.vb.yelplite.app.ui.main.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

val locationModule = module {
    factory { provideLocationManager(get()) }
    single { provideFusedLocationProviderClient(get()) }
}

fun provideLocationManager(context: Context): LocationManager {
    return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}

fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}

class YelpLiteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@YelpLiteApplication)
            modules(dataModule, networkModule, domainModule, fragmentModule, viewModelModule, locationModule)
        }
    }
}