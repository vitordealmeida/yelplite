package com.vb.yelplite.app

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vb.yelplite.app.data.dataModule
import com.vb.yelplite.app.data.networkModule
import com.vb.yelplite.app.domain.domainModule
import com.vb.yelplite.app.ui.uiModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

val deviceFeaturesModule = module {
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
            modules(dataModule, networkModule, domainModule, deviceFeaturesModule, uiModule)
        }
    }
}